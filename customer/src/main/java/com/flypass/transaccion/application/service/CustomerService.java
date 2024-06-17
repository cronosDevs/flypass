package com.flypass.transaccion.application.service;


import com.flypass.transaccion.application.port.in.CustomerUseCase;
import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.application.port.out.CustomerPort;
import com.flypass.transaccion.domain.Account;
import com.flypass.transaccion.domain.Customer;
import com.flypass.transaccion.exception.domain.BadRequestException;
import com.flypass.transaccion.util.MergeObjects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

/**
 * Service implementation for managing customers.
 * <p>
 * This service provides operations for creating, updating, retrieving, and deleting customers.
 * <p>
 * It uses {@link CustomerPort} for data persistence operations and {@link AccountPort}
 * for account-related operations.
 * <p>
 * Annotations:
 * - {@link Service}: Indicates that this class is a Spring service component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements CustomerUseCase {

    private final CustomerPort customerPort;
    private final AccountPort accountPort;

    /**
     * Creates a new customer.
     *
     * @param customer the customer to be created
     * @return the created customer
     * @throws BadRequestException if the customer is a minor or already exists
     */
    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Attempting to create customer with ID: {}", customer.numeroIdentificacion());
        checkMinor(customer);
        checkExistence(customer);
        customer = customer.toBuilder().enable(true).build();
        return customerPort.createCustomer(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param id       the ID of the customer to be updated
     * @param customer the updated customer details
     * @return the updated customer
     * @throws BadRequestException if the customer does not exist
     */
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        log.info("Attempting to update customer with ID: {}", id);
        var customerResponse = findById(id);
        var updatedCustomer = MergeObjects.mergeObjects(customerResponse, customer);
        return customerPort.createCustomer(updatedCustomer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to be deleted
     * @throws BadRequestException if the customer has associated accounts or does not exist
     */
    @Override
    public void deleteCustomer(Long id) {
        log.info("Attempting to delete customer with ID: {}", id);
        checkAccountExistence(id);
        var customerResponse = findById(id);
        var updatedCustomer = customerResponse.toBuilder().enable(false).build();
        customerPort.createCustomer(updatedCustomer);
    }

    /**
     * Checks if a customer has associated accounts.
     *
     * @param id the ID of the customer to be checked
     * @throws BadRequestException if the customer has associated accounts
     */
    public void checkAccountExistence(Long id) {
        log.info("Checking for accounts associated with customer ID: {}", id);
        List<Account> accountList = accountPort.findAccountByClienteId(id);
        if (!accountList.isEmpty()) {
            throw new BadRequestException("El cliente posee productos y no puede ser eliminado");
        }
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return the retrieved customer
     * @throws BadRequestException if the customer does not exist
     */
    @Override
    public Customer getCustomer(Long id) {
        log.info("Attempting to retrieve customer with ID: {}", id);
        return findById(id);
    }


    /**
     * Checks if the customer is a minor.
     *
     * @param customer the customer to be checked
     * @throws BadRequestException if the customer is a minor
     */
    private void checkMinor(Customer customer) {
        LocalDate nacimiento = customer.fechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (Period.between(nacimiento, LocalDate.now()).getYears() < 18) {
            log.warn("Customer with ID: {} is a minor", customer.numeroIdentificacion());
            throw new BadRequestException("The customer is minor");
        }
    }

    /**
     * Checks if a customer already exists based on their identification number.
     *
     * @param customer the customer to be checked
     * @throws BadRequestException if the customer already exists
     */
    private void checkExistence(Customer customer) {
        log.info("Checking existence of customer with ID: {}", customer.numeroIdentificacion());
        var customerResponse = customerPort.findByNumeroIdentificacion(customer.numeroIdentificacion());
        if (customerResponse.isPresent()) {
            throw new BadRequestException("The customer already exists");
        }
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be found
     * @return the found customer
     * @throws BadRequestException if the customer does not exist
     */
    private Customer findById(Long id) {
        log.info("Searching for customer with ID: {}", id);
        var customerResponse = customerPort.findById(id);
        if (customerResponse.isEmpty()) {
            log.warn("Customer with ID: {} not found", id);
            throw new BadRequestException("The customer doesn't exist");
        }
        return customerResponse.get();
    }
}
