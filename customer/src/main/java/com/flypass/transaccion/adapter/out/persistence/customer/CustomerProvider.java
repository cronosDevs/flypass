package com.flypass.transaccion.adapter.out.persistence.customer;


import com.flypass.transaccion.adapter.out.persistence.model.Cliente;

import java.util.Optional;

public interface CustomerProvider {

    Cliente createCustomer(Cliente cliente);

    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);

    Optional<Cliente> findById(Long clienteId);

}
