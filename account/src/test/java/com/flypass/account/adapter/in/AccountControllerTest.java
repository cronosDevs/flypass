package com.flypass.account.adapter.in;

import com.flypass.account.adapter.in.web.AccountController;
import com.flypass.account.adapter.in.web.mapper.AccountMapper;
import com.flypass.account.adapter.in.web.model.AccountResponseCommand;
import com.flypass.account.adapter.in.web.model.ChangeSaldoAccountCommand;
import com.flypass.account.adapter.in.web.model.ChangeStateAccountCommand;
import com.flypass.account.adapter.in.web.model.CreateAccountCommand;
import com.flypass.account.application.port.in.AccountUseCase;
import com.flypass.account.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class AccountControllerTest {

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountUseCase accountUseCase;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        Account account = Account.builder().build();
        AccountResponseCommand accountResponseCommand = AccountResponseCommand.builder().build();

        ResponseEntity<AccountResponseCommand> responseEntity = accountController.createAccount(createAccountCommand);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testChangeState() {
        ChangeStateAccountCommand changeStateAccountCommand = new ChangeStateAccountCommand();
        Account account = Account.builder().build();
        AccountResponseCommand accountResponseCommand = AccountResponseCommand.builder().build();

        ResponseEntity<AccountResponseCommand> responseEntity = accountController.changeState(changeStateAccountCommand, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetCuentasByClienteId() {
        List<Account> accounts = List.of(Account.builder().build());
        List<AccountResponseCommand> accountResponseCommands = List.of(AccountResponseCommand.builder().build());

        ResponseEntity<List<AccountResponseCommand>> responseEntity = accountController.getCuentasByClienteId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testChangeSaldo() {
        ChangeSaldoAccountCommand changeSaldoAccountCommand = new ChangeSaldoAccountCommand();
        Account account = Account.builder().build();
        AccountResponseCommand accountResponseCommand = AccountResponseCommand.builder().build();

        ResponseEntity<AccountResponseCommand> responseEntity = accountController.changeSaldo(changeSaldoAccountCommand, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
