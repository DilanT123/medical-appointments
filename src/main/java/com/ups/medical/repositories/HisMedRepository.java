package com.ups.medical.repositories;

import com.ups.medical.models.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HisMedRepository extends JpaRepository<HistorialMedico, Long> {
}
