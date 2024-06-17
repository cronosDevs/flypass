package com.flypass.transaccion.adapter.out.persistence.customer;


import com.flypass.transaccion.adapter.out.persistence.model.Cliente;
import com.flypass.transaccion.adapter.out.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerMySqlProvider implements CustomerProvider {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente createCustomer(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion) {
        return clienteRepository.findByNumeroIdentificacion(numeroIdentificacion);
    }

    @Override
    public Optional<Cliente> findById(Long clienteId) {
        return clienteRepository.findById(clienteId);
    }
}
