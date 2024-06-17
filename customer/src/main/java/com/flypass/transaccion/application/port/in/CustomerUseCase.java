package com.flypass.transaccion.application.port.in;


import com.flypass.transaccion.domain.Customer;

public interface CustomerUseCase {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomer(Long id);
}
