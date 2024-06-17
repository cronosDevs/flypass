package com.flypass.transaccion.adapter.out.persistence.transaction;


import com.flypass.transaccion.adapter.out.persistence.model.Transaccion;
import com.flypass.transaccion.adapter.out.persistence.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransaccionMySqlProvider implements TransaccionProvider {

    private final TransaccionRepository transaccionRepository;

    @Override
    public Transaccion createTransaction(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }
}
