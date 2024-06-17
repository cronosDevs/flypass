package com.flypass.transaccion.adapter.in.web;


import com.flypass.transaccion.adapter.in.web.mapper.CustomerMapper;
import com.flypass.transaccion.adapter.in.web.model.CreateCustomerCommand;
import com.flypass.transaccion.adapter.in.web.model.CustomerResponseCommand;
import com.flypass.transaccion.adapter.in.web.model.UpdateCustomerCommand;
import com.flypass.transaccion.application.port.in.CustomerUseCase;
import com.flypass.transaccion.domain.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing customers.
 * <p>
 * This controller provides endpoints for creating, updating, retrieving, and deleting customers.
 * <p>
 * It uses {@link CustomerMapper} to map between domain models and response commands,
 * and {@link CustomerUseCase} to handle business logic.
 * <p>
 * The controller endpoints are:
 * - POST /customer-api/v0/customers: Creates a new customer.
 * - PATCH /customer-api/v0/customers/{id}: Updates an existing customer.
 * - GET /customer-api/v0/customers/{id}: Retrieves a customer by ID.
 * - DELETE /customer-api/v0/customers/{id}: Deletes a customer by ID.
 * <p>
 * The controller ensures that input is validated using {@link Valid}.
 * <p>
 * Logging is enabled for the controller using {@link Slf4j}.
 * <p>
 * Annotations:
 * - {@link RestController}: Indicates that this class is a REST controller.
 * - {@link RequestMapping}: Maps requests to /customer-api/v0/customers.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/customer-api/v0/customers")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerUseCase customerUseCase;

    /**
     * Creates a new customer.
     *
     * @param createCustomerCommand the command containing customer creation details
     * @return a {@link ResponseEntity} containing the created customer
     */
    @PostMapping("")
    public ResponseEntity<CustomerResponseCommand> createCustomer(@Valid @RequestBody CreateCustomerCommand createCustomerCommand) {
        log.info("Received request to create customer with details: {}", createCustomerCommand);
        Customer customer = customerMapper.customerCommandToCustomer(createCustomerCommand);
        var response = customerUseCase.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.customerToCustomerResponseCommand(response));
    }

    /**
     * Updates an existing customer.
     *
     * @param updateCustomerCommand the command containing customer update details
     * @param id                    the ID of the customer to be updated
     * @return a {@link ResponseEntity} containing the updated customer
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseCommand> updateCustomer(@RequestBody UpdateCustomerCommand updateCustomerCommand,
                                                                  @PathVariable Long id) {
        log.info("Received request to update customer with ID: {}", id);
        Customer customer = customerMapper.updateCustomerCommandToCustomer(updateCustomerCommand);
        var response = customerUseCase.updateCustomer(id, customer);
        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.customerToCustomerResponseCommand(response));
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer
     * @return a {@link ResponseEntity} containing the customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseCommand> getCustomer(@PathVariable Long id) {
        log.info("Received request to get customer with ID: {}", id);
        var response = customerUseCase.getCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.customerToCustomerResponseCommand(response));
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.info("Received request to delete customer with ID: {}", id);
        customerUseCase.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
