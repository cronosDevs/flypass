package com.flypass.transaccion.application.port.out;


import com.flypass.transaccion.domain.Transaction;

public interface TransaccionPort {

    Transaction createTransaction(Transaction transaction);
}
