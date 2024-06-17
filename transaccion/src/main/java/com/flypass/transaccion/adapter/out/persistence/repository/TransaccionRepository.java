package com.flypass.transaccion.adapter.out.persistence.repository;

import com.flypass.transaccion.adapter.out.persistence.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

}
