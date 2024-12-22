package com.ups.medical.repositories;

import com.ups.medical.models.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones CRUD de RecetaMedica.
 */
@Repository
public interface RecMedRepository extends JpaRepository<RecetaMedica, Long> {
    // Métodos personalizados pueden definirse aquí si es necesario
}
