package com.flypass.transaccion.adapter.out.persistence.transaction;


import com.flypass.transaccion.adapter.out.persistence.model.Transaccion;

public interface TransaccionProvider {

    Transaccion createTransaction(Transaccion transaccion);
}
