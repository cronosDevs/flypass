package com.flypass.account.adapter.out.persistence.account;

import com.flypass.account.adapter.out.persistence.mapper.OutAccountMapper;
import com.flypass.account.application.port.out.AccountPort;
import com.flypass.account.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementation for account persistence.
 * <p>
 * This adapter uses {@link OutAccountMapper} to map between domain models and
 * persistence models, and {@link AccountProvider} to perform actual database operations.
 * <p>
 * Annotations:
 * - {@link Component}: Indicates that this class is a Spring component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AccountAdapter implements AccountPort {

    private final OutAccountMapper outAccountMapper;
    private final AccountProvider accountProvider;

    /**
     * Creates a new account.
     *
     * @param account the account to be created
     * @return the created account
     */
    @Override
    public Account createAccount(Account account) {
        var cuenta = outAccountMapper.accountToCuenta(account);
        return outAccountMapper.cuentaToAccount(accountProvider.createAccount(cuenta));
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the ID of the account
     * @return an {@link Optional} containing the account if found, or empty if not found
     */
    @Override
    public Optional<Account> findById(Long id) {
        var cuentaOptional = accountProvider.findById(id);
        return cuentaOptional.map(outAccountMapper::cuentaToAccount);
    }

    /**
     * Finds accounts by client ID.
     *
     * @param clientId the ID of the client
     * @return a list of accounts belonging to the client
     */
    @Override
    public List<Account> findByClientId(Long clientId) {
        var cuentaList = accountProvider.findByClientId(clientId);
        return cuentaList.stream().map(outAccountMapper::cuentaToAccount).toList();
    }
}
