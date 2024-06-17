package com.flypass.account.adapter.in.web;

import com.flypass.account.adapter.in.web.mapper.AccountMapper;
import com.flypass.account.adapter.in.web.model.AccountResponseCommand;
import com.flypass.account.adapter.in.web.model.ChangeSaldoAccountCommand;
import com.flypass.account.adapter.in.web.model.ChangeStateAccountCommand;
import com.flypass.account.adapter.in.web.model.CreateAccountCommand;
import com.flypass.account.application.port.in.AccountUseCase;
import com.flypass.account.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing accounts.
 * <p>
 * This controller provides endpoints for creating accounts, changing account states,
 * retrieving accounts by client ID, and updating account balances.
 * <p>
 * It uses {@link AccountMapper} to map between domain models and response commands,
 * and {@link AccountUseCase} to handle business logic.
 * <p>
 * The controller endpoints are:
 * - POST /account-api/v0/accounts: Creates a new account.
 * - PATCH /account-api/v0/accounts/{id}: Changes the state of an account.
 * - GET /account-api/v0/accounts: Retrieves accounts by client ID.
 * - PATCH /account-api/v0/accounts/{id}/saldo: Updates the balance of an account.
 * <p>
 * The controller ensures that input is validated using {@link Valid}.
 * <p>
 * Logging is enabled for the controller using {@link Slf4j}.
 * <p>
 * Annotations:
 * - {@link RestController}: Indicates that this class is a REST controller.
 * - {@link RequestMapping}: Maps requests to /account-api/v0/accounts.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/account-api/v0/accounts")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountUseCase accountUseCase;

    /**
     * Creates a new account.
     *
     * @param createAccountCommand the command containing account creation details
     * @return a {@link ResponseEntity} containing the created account
     */
    @PostMapping("")
    public ResponseEntity<AccountResponseCommand> createAccount(@Valid @RequestBody CreateAccountCommand createAccountCommand) {
        log.info("Received request to create account with details: {}", createAccountCommand);
        Account account = accountMapper.createAccountCommandToAccount(createAccountCommand);
        var response = accountUseCase.createAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(accountMapper.accountToAccountResponseCommand(response));
    }

    /**
     * Changes the state of an account.
     *
     * @param changeStateAccountCommand the command containing state change details
     * @param id                        the ID of the account to be updated
     * @return a {@link ResponseEntity} containing the updated account
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponseCommand> changeState(@Valid @RequestBody ChangeStateAccountCommand changeStateAccountCommand,
                                                              @PathVariable Long id) {
        log.info("Received request to change state of account with ID: {}", id);
        Account account = accountMapper.changeStateAccountCommandToAccount(changeStateAccountCommand);
        var response = accountUseCase.changeState(account, id);
        return ResponseEntity.status(HttpStatus.OK).body(accountMapper.accountToAccountResponseCommand(response));
    }

    /**
     * Retrieves accounts by client ID.
     *
     * @param clienteId the ID of the client
     * @return a {@link ResponseEntity} containing a list of accounts
     */
    @GetMapping("")
    public ResponseEntity<List<AccountResponseCommand>> getCuentasByClienteId(
            @RequestParam Long clienteId) {
        var response = accountUseCase.getCuentasByClienteId(clienteId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.stream().map(accountMapper::accountToAccountResponseCommand).toList());
    }

    /**
     * Updates the balance of an account.
     *
     * @param changeSaldoAccountCommand the command containing balance update details
     * @param id                        the ID of the account to be updated
     * @return a {@link ResponseEntity} containing the updated account
     */
    @PatchMapping("/{id}/saldo")
    public ResponseEntity<AccountResponseCommand> changeSaldo(@Valid @RequestBody ChangeSaldoAccountCommand changeSaldoAccountCommand,
                                                              @PathVariable Long id) {
        log.info("Received request to change balance of account with ID: {}", id);
        Account account = accountMapper.changeSaldoAccountCommandToAccount(changeSaldoAccountCommand);
        var response = accountUseCase.changeSaldo(account, id);
        return ResponseEntity.status(HttpStatus.OK).body(accountMapper.accountToAccountResponseCommand(response));
    }

}
