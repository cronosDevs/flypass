package com.flypass.transaccion.application.service;

import com.flypass.transaccion.adapter.out.rest.account.model.ChangeSaldoAccountCommand;
import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.application.port.out.TransaccionPort;
import com.flypass.transaccion.domain.Transaction;
import com.flypass.transaccion.domain.enums.TipoTransactionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransaccionPort transaccionPort;

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConsignment() {
        Transaction transaction = createTestTransaction();
        Transaction savedTransaction = transaction.toBuilder().tipoTransaccion(TipoTransactionEnum.CONSIGNACION).build();

        when(transaccionPort.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createConsignment(transaction);

        assertEquals(TipoTransactionEnum.CONSIGNACION, result.tipoTransaccion());
        verify(accountPort, times(1)).changeSaldoByAccountId(eq(transaction.cuentaDestino()), any(ChangeSaldoAccountCommand.class));
        verify(transaccionPort, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void testCreateWithdraw() {
        Transaction transaction = createTestTransaction();
        Transaction savedTransaction = transaction.toBuilder().tipoTransaccion(TipoTransactionEnum.RETIRO).build();

        when(transaccionPort.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createWithdraw(transaction);

        assertEquals(TipoTransactionEnum.RETIRO, result.tipoTransaccion());
        verify(accountPort, times(1)).changeSaldoByAccountId(eq(transaction.cuentaDestino()), any(ChangeSaldoAccountCommand.class));
        verify(transaccionPort, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void testCreateTransfer() {
        Transaction transaction = createTestTransaction();
        Transaction savedTransaction = transaction.toBuilder().tipoTransaccion(TipoTransactionEnum.TRANSFERENCIA).build();

        when(transaccionPort.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createTransfer(transaction);

        assertEquals(TipoTransactionEnum.TRANSFERENCIA, result.tipoTransaccion());
        verify(accountPort, times(1)).changeSaldoByAccountId(eq(transaction.cuentaOrigen()), any(ChangeSaldoAccountCommand.class));
        verify(accountPort, times(1)).changeSaldoByAccountId(eq(transaction.cuentaDestino()), any(ChangeSaldoAccountCommand.class));
        verify(transaccionPort, times(1)).createTransaction(any(Transaction.class));
    }

    private Transaction createTestTransaction() {
        return Transaction.builder()
                .id(1L)
                .monto(BigDecimal.valueOf(100))
                .cuentaOrigen(1L)
                .cuentaDestino(2L)
                .build();
    }

}
