package com.flypass.account.adapter.out.persistence.mapper;

import com.flypass.account.adapter.out.persistence.model.Cuenta;
import com.flypass.account.domain.Account;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OutAccountMapper {

    Cuenta accountToCuenta(Account account);

    Account cuentaToAccount(Cuenta cuenta);

}
