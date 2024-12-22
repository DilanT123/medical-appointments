package com.ups.medical.repositories;

import com.ups.medical.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRespository extends JpaRepository<Doctor, Long> {
}
