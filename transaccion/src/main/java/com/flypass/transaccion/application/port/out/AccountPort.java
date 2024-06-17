package com.flypass.transaccion.application.port.out;

import com.flypass.transaccion.adapter.out.rest.account.model.ChangeSaldoAccountCommand;
import com.flypass.transaccion.domain.Account;

public interface AccountPort {

    Account changeSaldoByAccountId(Long accountId, ChangeSaldoAccountCommand changeSaldoAccountCommand);
}
