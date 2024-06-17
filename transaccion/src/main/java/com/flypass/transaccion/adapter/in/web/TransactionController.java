package com.flypass.transaccion.adapter.in.web;


import com.flypass.transaccion.adapter.in.web.mapper.TransactionMapper;
import com.flypass.transaccion.adapter.in.web.model.CreateConsignmentCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateTransferCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateWithdrawCommand;
import com.flypass.transaccion.adapter.in.web.model.TransactionResponseCommand;
import com.flypass.transaccion.application.port.in.TransactionUseCase;
import com.flypass.transaccion.domain.Transaction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing transactions.
 * <p>
 * This controller provides endpoints for creating consignments, withdrawals, and transfers.
 * <p>
 * It uses {@link TransactionMapper} to map between domain models and response commands,
 * and {@link TransactionUseCase} to handle business logic.
 * <p>
 * The controller endpoints are:
 * - POST /transaccion-api/v0/transactions/consignments: Creates a new consignment.
 * - POST /transaccion-api/v0/transactions/withdraws: Creates a new withdrawal.
 * - POST /transaccion-api/v0/transactions/transfers: Creates a new transfer.
 * <p>
 * The controller ensures that input is validated using {@link Valid}.
 * <p>
 * Logging is enabled for the controller using {@link Slf4j}.
 * <p>
 * Annotations:
 * - {@link RestController}: Indicates that this class is a REST controller.
 * - {@link RequestMapping}: Maps requests to /transaccion-api/v0/transactions.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/transaccion-api/v0/transactions")
public class TransactionController {

    private final TransactionMapper transactionMapper;

    private final TransactionUseCase transactionUseCase;

    /**
     * Creates a new consignment.
     *
     * @param createConsignmentCommand the command containing consignment creation details
     * @return a {@link ResponseEntity} containing the created consignment
     */
    @PostMapping("/consignments")
    public ResponseEntity<TransactionResponseCommand> createConsignment
    (@Valid @RequestBody CreateConsignmentCommand createConsignmentCommand) {
        log.info("Received request to create consignment with details: {}", createConsignmentCommand);
        Transaction transaction = transactionMapper.createConsignmentCommandToTransaction(createConsignmentCommand);
        var response = transactionUseCase.createConsignment(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.transactionToTransactionResponseCommand(response));
    }

    /**
     * Creates a new withdrawal.
     *
     * @param createWithdrawCommand the command containing withdrawal creation details
     * @return a {@link ResponseEntity} containing the created withdrawal
     */
    @PostMapping("/withdraws")
    public ResponseEntity<TransactionResponseCommand> createWithdraw
    (@Valid @RequestBody CreateWithdrawCommand createWithdrawCommand) {
        log.info("Received request to create withdrawal with details: {}", createWithdrawCommand);
        Transaction transaction = transactionMapper.createWithdrawCommandToTransaction(createWithdrawCommand);
        var response = transactionUseCase.createWithdraw(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.transactionToTransactionResponseCommand(response));
    }

    /**
     * Creates a new transfer.
     *
     * @param createTransferCommand the command containing transfer creation details
     * @return a {@link ResponseEntity} containing the created transfer
     */
    @PostMapping("/transfers")
    public ResponseEntity<TransactionResponseCommand> createTransfer
    (@Valid @RequestBody CreateTransferCommand createTransferCommand) {
        log.info("Received request to create transfer with details: {}", createTransferCommand);
        Transaction transaction = transactionMapper.createTransferCommandToTransaction(createTransferCommand);
        var response = transactionUseCase.createTransfer(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.transactionToTransactionResponseCommand(response));
    }

}
