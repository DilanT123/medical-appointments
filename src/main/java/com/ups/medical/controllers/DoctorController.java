package com.ups.medical.controllers;

import com.ups.medical.models.Doctor;
import com.ups.medical.repositories.DoctorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorRespository doctorRespository;

    @GetMapping
    public List<Doctor> getAllDoctor(){
        return doctorRespository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor){
        return doctorRespository.save(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails){
        return doctorRespository.findById(id)
                .map(doctor -> {
                    doctor.setApellido(doctorDetails.getApellido());
                    doctor.setNombre(doctorDetails.getNombre());
                    doctor.setHorarioAtencion((doctorDetails.getHorarioAtencion()));
                    doctor.setNumeroLicencia(doctorDetails.getNumeroLicencia());
                    doctor.setConsultorio(doctorDetails.getConsultorio());
                    doctor.setEspecialidad(doctorDetails.getEspecialidad());
                    return ResponseEntity.ok(doctorRespository.save(doctor));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable Long id){
        return doctorRespository.findById(id)
                .map(doctor -> {
                    doctorRespository.delete(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
