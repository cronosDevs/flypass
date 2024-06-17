package com.flypass.account.adapter.out.persistence.account;


import com.flypass.account.adapter.out.persistence.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface AccountProvider {

    Cuenta createAccount(Cuenta cuenta);

    Optional<Cuenta> findById(Long id);

    List<Cuenta> findByClientId(Long clientId);
}
