package com.flypass.account.application.port.out;


import com.flypass.account.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountPort {
    Account createAccount(Account customer);

    Optional<Account> findById(Long id);

    List<Account> findByClientId(Long clientId);
}
