package com.flypass.transaccion.adapter.out.persistence.customer;


import com.flypass.transaccion.adapter.out.persistence.mapper.OutCustomerMapper;
import com.flypass.transaccion.application.port.out.CustomerPort;
import com.flypass.transaccion.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adapter implementation for customer persistence.
 * <p>
 * This adapter uses {@link OutCustomerMapper} to map between domain models and
 * persistence models, and {@link CustomerProvider} to perform actual database operations.
 * <p>
 * Annotations:
 * - {@link Component}: Indicates that this class is a Spring component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerAdapter implements CustomerPort {

    private final OutCustomerMapper outCustomerMapper;
    private final CustomerProvider customerProvider;

    /**
     * Creates a new customer.
     *
     * @param customer the customer to be created
     * @return the created customer
     */
    @Override
    public Customer createCustomer(Customer customer) {
        var cliente = outCustomerMapper.customerToCliente(customer);
        return outCustomerMapper.clienteToCustomer(customerProvider.createCustomer(cliente));
    }


    /**
     * Finds a customer by their identification number.
     *
     * @param numeroIdentificacion the identification number of the customer
     * @return an {@link Optional} containing the customer if found, or empty if not found
     */
    @Override
    public Optional<Customer> findByNumeroIdentificacion(String numeroIdentificacion) {
        var clienteOptional = customerProvider.findByNumeroIdentificacion(numeroIdentificacion);
        return clienteOptional.map(outCustomerMapper::clienteToCustomer);
    }

    /**
     * Finds a customer by their ID.
     *
     * @param customerId the ID of the customer
     * @return an {@link Optional} containing the customer if found, or empty if not found
     */
    @Override
    public Optional<Customer> findById(Long customerId) {
        var clienteOptional = customerProvider.findById(customerId);
        return clienteOptional.map(outCustomerMapper::clienteToCustomer);
    }
}
