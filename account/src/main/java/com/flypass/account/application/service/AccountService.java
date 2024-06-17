package com.flypass.account.application.service;


import com.flypass.account.application.port.in.AccountUseCase;
import com.flypass.account.application.port.out.AccountPort;
import com.flypass.account.application.port.out.CustomerPort;
import com.flypass.account.domain.Account;
import com.flypass.account.domain.enums.EstadoCuentaEnum;
import com.flypass.account.domain.enums.TipoCuentaEnum;
import com.flypass.account.exception.domain.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

/**
 * Service implementation for managing accounts.
 * <p>
 * This service provides operations for creating accounts, changing account states,
 * retrieving accounts by client ID, and updating account balances.
 * <p>
 * It uses {@link AccountPort} for data persistence operations and {@link CustomerPort}
 * for customer-related operations.
 * <p>
 * Annotations:
 * - {@link Service}: Indicates that this class is a service component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements AccountUseCase {

    private final AccountPort accountPort;
    private final CustomerPort customerPort;

    private static final String START_NUMBER_CUENTA_CORRIENTE = "33";
    private static final String START_NUMBER_CUENTA_AHORROS = "53";

    /**
     * Creates a new account.
     *
     * @param account the account to be created
     * @return the created account
     */
    @Override
    public Account createAccount(Account account) {
        log.info("Attempting to create a new account for customer ID: {}", account.clienteId());
        checkConstrains(account);
        account = account.toBuilder().estado(EstadoCuentaEnum.ACTIVA)
                .numeroCuenta(generarNumeroCuenta(account))
                .build();
        return accountPort.createAccount(account);
    }

    /**
     * Changes the state of an account.
     *
     * @param account the account with the new state
     * @param id      the ID of the account to be updated
     * @return the updated account
     */
    @Override
    public Account changeState(Account account, Long id) {
        log.info("Attempting to change the state of account with ID: {}", id);
        var accountResponse = findById(id);
        if (account.estado().equals(EstadoCuentaEnum.CANCELADA)) {
            checkCancelSaldo(accountResponse);
        }
        var accountUpdated = accountResponse.toBuilder().estado(account.estado()).build();
        return accountPort.createAccount(accountUpdated);
    }

    /**
     * Retrieves accounts by client ID.
     *
     * @param clienteId the ID of the client
     * @return a list of accounts
     */
    @Override
    public List<Account> getCuentasByClienteId(Long clienteId) {
        log.info("Retrieving accounts for client ID: {}", clienteId);
        return accountPort.findByClientId(clienteId);
    }

    /**
     * Updates the balance of an account.
     *
     * @param account the account with the new balance
     * @param id      the ID of the account to be updated
     * @return the updated account
     */
    @Override
    public Account changeSaldo(Account account, Long id) {
        log.info("Attempting to change the balance of account with ID: {}", id);
        var accountResponse = findById(id);
        if (account.saldo().compareTo(BigDecimal.ZERO) < 0) {
            accountResponse = accountResponse.toBuilder().saldo(discountBalance(accountResponse, account)).build();
        } else {
            accountResponse = accountResponse.toBuilder().saldo(addBalance(accountResponse, account)).build();
        }
        return accountPort.createAccount(accountResponse);
    }


    /**
     * Reduces the balance of an account.
     *
     * @param accountResponse the current account details
     * @param account         the account with the amount to be deducted
     * @return the new balance
     */
    private BigDecimal discountBalance(Account accountResponse, Account account) {
        log.info("Discounting balance for account ID: {}", accountResponse.id());
        BigDecimal newBalance = accountResponse.saldo().add(account.saldo());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("No tiene suficiente balance");
        }
        return newBalance;
    }

    /**
     * Increases the balance of an account.
     *
     * @param accountResponse the current account details
     * @param account         the account with the amount to be added
     * @return the new balance
     */
    private BigDecimal addBalance(Account accountResponse, Account account) {
        log.info("Adding balance to account ID: {}", accountResponse.id());
        return accountResponse.saldo().add(account.saldo());
    }

    /**
     * Checks if an account can be canceled based on its balance.
     *
     * @param account the account to be checked
     */
    private void checkCancelSaldo(Account account) {
        log.info("Checking if account ID: {} can be canceled", account.id());
        if (account.saldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("El saldo debe ser igual a cero para cancelar la cuenta");
        }
    }

    /**
     * Generates an account number based on the account type.
     *
     * @param account the account for which the number is to be generated
     * @return the generated account number
     */
    private String generarNumeroCuenta(Account account) {
        log.info("Generating account number for account type: {}", account.tipoCuenta());
        if (account.tipoCuenta().equals(TipoCuentaEnum.CUENTA_CORRIENTE)) {
            return generateNumeroCuenta(START_NUMBER_CUENTA_CORRIENTE);
        } else if (account.tipoCuenta().equals(TipoCuentaEnum.CUENTA_AHORROS)) {
            return generateNumeroCuenta(START_NUMBER_CUENTA_AHORROS);
        }
        return "";
    }

    /**
     * Generates a random account number starting with the given prefix.
     *
     * @param startNumber the prefix for the account number
     * @return the generated account number
     */
    private String generateNumeroCuenta(String startNumber) {
        SecureRandom random = new SecureRandom();
        long randomNumber = 10000000 + random.nextInt(90000000);
        return startNumber + randomNumber;
    }

    /**
     * Checks constraints on the account before creation.
     *
     * @param account the account to be checked
     */
    private void checkConstrains(Account account) {
        log.info("Checking constraints for account creation for customer ID: {}", account.clienteId());
        checkCustomerExists(account);
        if (account.tipoCuenta().equals(TipoCuentaEnum.CUENTA_AHORROS)) {
            checkSaldo(account);
        }
    }

    /**
     * Checks if the account balance is non-negative.
     *
     * @param account the account to be checked
     */
    private void checkSaldo(Account account) {
        log.info("Checking if account balance for customer ID: {} is non-negative", account.clienteId());
        if (account.saldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Saldo no puede ser menor a cero");
        }
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the ID of the account
     * @return the account if found
     * @throws BadRequestException if the account does not exist
     */
    private Account findById(Long id) {
        log.info("Searching for account with ID: {}", id);
        var customerResponse = accountPort.findById(id);
        if (customerResponse.isEmpty()) {
            throw new BadRequestException("The account doesn't exist");
        }
        return customerResponse.get();
    }

    /**
     * Checks if the customer exists for the given account.
     *
     * @param account the account whose customer is to be checked
     */
    private void checkCustomerExists(Account account) {
        log.info("Checking if customer exists for customer ID: {}", account.clienteId());
        customerPort.findCustomerById(account.clienteId());
    }
}
