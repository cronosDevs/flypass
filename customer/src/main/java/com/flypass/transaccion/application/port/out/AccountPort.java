package com.flypass.transaccion.application.port.out;

import com.flypass.transaccion.domain.Account;

import java.util.List;

public interface AccountPort {

    List<Account> findAccountByClienteId(Long clienteId);
}
