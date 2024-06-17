package com.flypass.transaccion.adapter.out.persistence.mapper;


import com.flypass.transaccion.adapter.out.persistence.model.Cliente;
import com.flypass.transaccion.domain.Customer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OutCustomerMapper {

    Cliente customerToCliente(Customer customer);

    Customer clienteToCustomer(Cliente cliente);

}
