package com.flypass.transaccion.application.port.in;


import com.flypass.transaccion.domain.Transaction;

public interface TransactionUseCase {

    Transaction createConsignment(Transaction transaction);

    Transaction createWithdraw(Transaction transaction);

    Transaction createTransfer(Transaction transaction);
}
