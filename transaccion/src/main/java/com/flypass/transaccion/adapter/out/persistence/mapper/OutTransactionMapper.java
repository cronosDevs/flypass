package com.flypass.transaccion.adapter.out.persistence.mapper;


import com.flypass.transaccion.adapter.out.persistence.model.Transaccion;
import com.flypass.transaccion.domain.Transaction;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OutTransactionMapper {

    Transaccion transactionToTransaccion(Transaction transaction);

    Transaction transaccionToTransaction(Transaccion transaccion);

}
