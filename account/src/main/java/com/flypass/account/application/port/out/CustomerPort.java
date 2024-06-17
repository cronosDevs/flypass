package com.flypass.account.application.port.out;


import com.flypass.account.domain.Customer;

import java.util.Optional;

public interface CustomerPort {

    Optional<Customer> findCustomerById(Long id);

}
