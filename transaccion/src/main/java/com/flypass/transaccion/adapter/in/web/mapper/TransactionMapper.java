package com.flypass.transaccion.adapter.in.web.mapper;

import com.flypass.transaccion.adapter.in.web.model.CreateConsignmentCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateTransferCommand;
import com.flypass.transaccion.adapter.in.web.model.CreateWithdrawCommand;
import com.flypass.transaccion.adapter.in.web.model.TransactionResponseCommand;
import com.flypass.transaccion.domain.Transaction;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TransactionMapper {

    Transaction createConsignmentCommandToTransaction(CreateConsignmentCommand createConsignmentCommand);

    Transaction createWithdrawCommandToTransaction(CreateWithdrawCommand createWithdrawCommand);

    Transaction createTransferCommandToTransaction(CreateTransferCommand createTransferCommand);

    TransactionResponseCommand transactionToTransactionResponseCommand(Transaction transaction);

}
