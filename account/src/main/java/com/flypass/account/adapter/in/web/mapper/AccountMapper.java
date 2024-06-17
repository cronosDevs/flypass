package com.flypass.account.adapter.in.web.mapper;

import com.flypass.account.adapter.in.web.model.AccountResponseCommand;
import com.flypass.account.adapter.in.web.model.ChangeSaldoAccountCommand;
import com.flypass.account.adapter.in.web.model.ChangeStateAccountCommand;
import com.flypass.account.adapter.in.web.model.CreateAccountCommand;
import com.flypass.account.domain.Account;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AccountMapper {

    Account createAccountCommandToAccount(CreateAccountCommand createAccountCommand);

    AccountResponseCommand accountToAccountResponseCommand(Account account);

    Account changeStateAccountCommandToAccount(ChangeStateAccountCommand changeStateAccountCommand);

    Account changeSaldoAccountCommandToAccount(ChangeSaldoAccountCommand changeSaldoAccountCommand);

}
