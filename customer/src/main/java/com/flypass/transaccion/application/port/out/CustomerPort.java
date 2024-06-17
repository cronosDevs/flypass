package com.flypass.transaccion.application.port.out;


import com.flypass.transaccion.domain.Customer;

import java.util.Optional;

public interface CustomerPort {

    Customer createCustomer(Customer customer);

    Optional<Customer> findByNumeroIdentificacion(String numeroIdentificacion);

    Optional<Customer> findById(Long customerId);

}
