package com.ups.medical.repositories;

import com.ups.medical.models.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<Consultorio, Long> {
}
