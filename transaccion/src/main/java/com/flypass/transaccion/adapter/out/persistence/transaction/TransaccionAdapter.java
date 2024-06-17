package com.flypass.transaccion.adapter.out.persistence.transaction;


import com.flypass.transaccion.adapter.out.persistence.mapper.OutTransactionMapper;
import com.flypass.transaccion.application.port.out.TransaccionPort;
import com.flypass.transaccion.domain.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation for transaction persistence.
 * <p>
 * This adapter uses {@link OutTransactionMapper} to map between domain models and
 * persistence models, and {@link TransaccionProvider} to perform actual database operations.
 * <p>
 * Annotations:
 * - {@link Component}: Indicates that this class is a Spring component.
 * - {@link RequiredArgsConstructor}: Generates a constructor with required arguments.
 * - {@link Slf4j}: Enables logging.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TransaccionAdapter implements TransaccionPort {

    private final OutTransactionMapper outTransactionMapper;
    private final TransaccionProvider transaccionProvider;

    /**
     * Creates a new transaction.
     *
     * @param transaction the transaction to be created
     * @return the created transaction
     */
    @Override
    public Transaction createTransaction(Transaction transaction) {
        var cliente = outTransactionMapper.transactionToTransaccion(transaction);
        return outTransactionMapper.transaccionToTransaction(transaccionProvider.createTransaction(cliente));
    }
}