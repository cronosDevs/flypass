package com.flypass.account.adapter.out.persistence.account;

import com.flypass.account.adapter.out.persistence.model.Cuenta;
import com.flypass.account.adapter.out.persistence.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountMySqlProvider implements AccountProvider {

    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta createAccount(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public List<Cuenta> findByClientId(Long clientId) {
        return cuentaRepository.findAllByClienteId(clientId);
    }

}
