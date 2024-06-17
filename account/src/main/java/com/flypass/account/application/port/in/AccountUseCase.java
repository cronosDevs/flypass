package com.flypass.account.application.port.in;


import com.flypass.account.domain.Account;

import java.util.List;

public interface AccountUseCase {

    Account createAccount(Account account);

    Account changeState(Account account, Long id);

    List<Account> getCuentasByClienteId(Long clienteId);

    Account changeSaldo(Account account, Long id);
}
