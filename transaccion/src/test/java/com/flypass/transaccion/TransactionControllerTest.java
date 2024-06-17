package com.flypass.transaccion;

import com.flypass.transaccion.adapter.in.web.TransactionController;
import com.flypass.transaccion.adapter.in.web.mapper.TransactionMapper;
import com.flypass.transaccion.adapter.in.web.model.CreateConsignmentCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateTransferCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateWithdrawCommand;
import com.flypass.transaccion.adapter.in.web.model.TransactionResponseCommand;
import com.flypass.transaccion.application.port.in.TransactionUseCase;
import com.flypass.transaccion.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {


    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private TransactionUseCase transactionUseCase;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConsignment() {
        CreateConsignmentCommand createConsignmentCommand = new CreateConsignmentCommand();
        Transaction transaction = Transaction.builder().build();
        TransactionResponseCommand transactionResponseCommand = TransactionResponseCommand.builder().build();

        when(transactionMapper.createConsignmentCommandToTransaction(any(CreateConsignmentCommand.class))).thenReturn(transaction);
        when(transactionUseCase.createConsignment(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.transactionToTransactionResponseCommand(any(Transaction.class))).thenReturn(transactionResponseCommand);

        ResponseEntity<TransactionResponseCommand> responseEntity = transactionController.createConsignment(createConsignmentCommand);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionResponseCommand, responseEntity.getBody());
    }

    @Test
    void testCreateWithdraw() {
        CreateWithdrawCommand createWithdrawCommand = new CreateWithdrawCommand();
        Transaction transaction = Transaction.builder().build();
        TransactionResponseCommand transactionResponseCommand = TransactionResponseCommand.builder().build();

        when(transactionMapper.createWithdrawCommandToTransaction(any(CreateWithdrawCommand.class))).thenReturn(transaction);
        when(transactionUseCase.createWithdraw(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.transactionToTransactionResponseCommand(any(Transaction.class))).thenReturn(transactionResponseCommand);

        ResponseEntity<TransactionResponseCommand> responseEntity = transactionController.createWithdraw(createWithdrawCommand);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionResponseCommand, responseEntity.getBody());
    }

    @Test
    void testCreateTransfer() {
        CreateTransferCommand createTransferCommand = new CreateTransferCommand();
        Transaction transaction = Transaction.builder().build();
        TransactionResponseCommand transactionResponseCommand = TransactionResponseCommand.builder().build();

        when(transactionMapper.createTransferCommandToTransaction(any(CreateTransferCommand.class))).thenReturn(transaction);
        when(transactionUseCase.createTransfer(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.transactionToTransactionResponseCommand(any(Transaction.class))).thenReturn(transactionResponseCommand);

        ResponseEntity<TransactionResponseCommand> responseEntity = transactionController.createTransfer(createTransferCommand);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionResponseCommand, responseEntity.getBody());
    }

}
