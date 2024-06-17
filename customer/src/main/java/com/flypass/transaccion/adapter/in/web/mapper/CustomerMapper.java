package com.flypass.transaccion.adapter.in.web.mapper;


import com.flypass.transaccion.adapter.in.web.model.CreateCustomerCommand;
import com.flypass.transaccion.adapter.in.web.model.CustomerResponseCommand;
import com.flypass.transaccion.adapter.in.web.model.UpdateCustomerCommand;
import com.flypass.transaccion.domain.Customer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerMapper {

    Customer customerCommandToCustomer(CreateCustomerCommand createCustomerCommand);

    CustomerResponseCommand customerToCustomerResponseCommand(Customer customer);

    Customer updateCustomerCommandToCustomer(UpdateCustomerCommand updateCustomerCommand);

}
