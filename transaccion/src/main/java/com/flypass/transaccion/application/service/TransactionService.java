package com.flypass.transaccion.application.service;


import com.flypass.transaccion.adapter.out.rest.account.model.ChangeSaldoAccountCommand;
import com.flypass.transaccion.application.port.in.TransactionUseCase;
import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.application.port.out.TransaccionPort;
import com.flypass.transaccion.domain.Transaction;
import com.flypass.transaccion.domain.enums.TipoTransactionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service implementation for managing transactions.
 * <p>
 * This service provides operations for creating consignments, withdrawals, and transfers.
 * <p>
 * It uses {@link TransaccionPort} for data persistence operations and {@link AccountPort}
 * for account-related operations.
 * <p>
 * Annotations:
 * - {@link Service}: Indicates that this class is a Spring service component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService implements TransactionUseCase {

    private final TransaccionPort transaccionPort;
    private final AccountPort accountPort;

    /**
     * Creates a new consignment transaction.
     *
     * @param transaction the transaction details
     * @return the created transaction
     */
    @Override
    public Transaction createConsignment(Transaction transaction) {
        log.info("Creating consignment transaction for destination account: {}", transaction.cuentaDestino());
        var transactionUpdated = transaction.toBuilder()
                .monto(transaction.monto())
                .tipoTransaccion(TipoTransactionEnum.CONSIGNACION).build();
        accountPort.changeSaldoByAccountId(transaction.cuentaDestino(),
                ChangeSaldoAccountCommand.builder().saldo(transaction.monto()).build());
        return transaccionPort.createTransaction(transactionUpdated);
    }

    /**
     * Creates a new withdrawal transaction.
     *
     * @param transaction the transaction details
     * @return the created transaction
     */
    @Override
    public Transaction createWithdraw(Transaction transaction) {
        log.info("Creating withdrawal transaction for destination account: {}", transaction.cuentaDestino());
        var transactionUpdated = transaction.toBuilder()
                .monto(transaction.monto())
                .tipoTransaccion(TipoTransactionEnum.RETIRO).build();
        accountPort.changeSaldoByAccountId(transaction.cuentaDestino(),
                ChangeSaldoAccountCommand.builder()
                        .saldo(transaction.monto().multiply(BigDecimal.valueOf(-1))).build());
        return transaccionPort.createTransaction(transactionUpdated);
    }

    /**
     * Creates a new transfer transaction.
     *
     * @param transaction the transaction details
     * @return the created transaction
     */
    @Override
    public Transaction createTransfer(Transaction transaction) {
        log.info("Creating transfer transaction from account: {} to account: {}", transaction.cuentaOrigen(), transaction.cuentaDestino());
        var transactionUpdated = transaction.toBuilder()
                .monto(transaction.monto())
                .tipoTransaccion(TipoTransactionEnum.TRANSFERENCIA).build();
        accountPort.changeSaldoByAccountId(transaction.cuentaOrigen(),
                ChangeSaldoAccountCommand.builder()
                        .saldo(transaction.monto().multiply(BigDecimal.valueOf(-1))).build());
        accountPort.changeSaldoByAccountId(transaction.cuentaDestino(),
                ChangeSaldoAccountCommand.builder()
                        .saldo(transaction.monto()).build());
        return transaccionPort.createTransaction(transactionUpdated);
    }

}
