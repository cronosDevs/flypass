package com.flypass.account.application.service;

import com.flypass.account.application.port.out.AccountPort;
import com.flypass.account.application.port.out.CustomerPort;
import com.flypass.account.domain.Account;
import com.flypass.account.domain.Customer;
import com.flypass.account.domain.enums.EstadoCuentaEnum;
import com.flypass.account.domain.enums.TipoCuentaEnum;
import com.flypass.account.exception.domain.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountPort accountPort;

    @Mock
    private CustomerPort customerPort;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        Account account = Account.builder()
                .clienteId(1L)
                .tipoCuenta(TipoCuentaEnum.CUENTA_AHORROS)
                .saldo(BigDecimal.TEN)
                .build();

        when(accountPort.createAccount(any(Account.class))).thenReturn(account);
        when(customerPort.findCustomerById(anyLong())).thenReturn(Optional.of(Customer.builder().build()));

        Account createdAccount = accountService.createAccount(account);

        assertNotNull(createdAccount);
        verify(accountPort, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testChangeState() {
        Account account = Account.builder()
                .estado(EstadoCuentaEnum.CANCELADA)
                .build();
        Account existingAccount = Account.builder()
                .id(1L)
                .estado(EstadoCuentaEnum.ACTIVA)
                .saldo(BigDecimal.ZERO)
                .build();

        when(accountPort.findById(anyLong())).thenReturn(Optional.of(existingAccount));
        when(accountPort.createAccount(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.changeState(account, 1L);

        assertNotNull(updatedAccount);
        assertEquals(EstadoCuentaEnum.CANCELADA, updatedAccount.estado());
        verify(accountPort, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testGetCuentasByClienteId() {
        List<Account> accounts = List.of(Account.builder().build());

        when(accountPort.findByClientId(anyLong())).thenReturn(accounts);

        List<Account> retrievedAccounts = accountService.getCuentasByClienteId(1L);

        assertNotNull(retrievedAccounts);
        assertFalse(retrievedAccounts.isEmpty());
        verify(accountPort, times(1)).findByClientId(anyLong());
    }

    @Test
    void testChangeSaldo() {
        Account account = Account.builder()
                .saldo(BigDecimal.TEN)
                .build();
        Account existingAccount = Account.builder()
                .id(1L)
                .saldo(BigDecimal.ZERO)
                .build();

        when(accountPort.findById(anyLong())).thenReturn(Optional.of(existingAccount));
        when(accountPort.createAccount(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.changeSaldo(account, 1L);

        assertNotNull(updatedAccount);
        assertEquals(BigDecimal.TEN, updatedAccount.saldo());
        verify(accountPort, times(1)).createAccount(any(Account.class));
    }


}
