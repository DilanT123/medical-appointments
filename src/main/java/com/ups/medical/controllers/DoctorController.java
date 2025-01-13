package com.ups.medical.controllers;

import com.ups.medical.models.Consultorio;
import com.ups.medical.models.Doctor;
import com.ups.medical.models.Especialidad;
import com.ups.medical.repositories.ConsultRepository;
import com.ups.medical.repositories.DoctorRespository;
import com.ups.medical.repositories.EspecialidadRepository;
import com.ups.medical.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Doctor", description = "API para la gestión de doctores del sistema médico")
public class DoctorController {

    private final DoctorRespository doctorRespository;
    private final EspecialidadRepository especialidadRepository;
    private final ConsultRepository consultorioRepository;
    private final RolRepository rolRepository;

    @Autowired
    public DoctorController(DoctorRespository doctorRespository,
                            EspecialidadRepository especialidadRepository,
                            ConsultRepository consultorioRepository, RolRepository rolRepository) {
        this.doctorRespository = doctorRespository;
        this.especialidadRepository = especialidadRepository;
        this.consultorioRepository = consultorioRepository;
        this.rolRepository = rolRepository;
    }

    @Operation(summary = "Obtener todos los doctores")
    @GetMapping
    public List<Doctor> getAllDoctor() {
        return doctorRespository.findAll();
    }

    @Operation(summary = "Obtener doctor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo doctor")
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctorRequest) {
        Especialidad especialidad = findEspecialidadById(doctorRequest.getEspecialidad().getId());
        Consultorio consultorio = findConsultorioById(doctorRequest.getConsultorio().getId());

        Doctor doctor = new Doctor(
                        doctorRequest.getNombre(),
                        doctorRequest.getApellido(),
                        doctorRequest.getCedula(),
                        doctorRequest.getTelefono(),
                        doctorRequest.getEmail(),
                        doctorRequest.getPassword(),
                        doctorRequest.getUsername(),
                doctorRequest.getNumeroLicencia(),
                doctorRequest.getHorarioAtencion(),
                especialidad,
                consultorio,
                doctorRequest.getRol()
                );

        return ResponseEntity.ok(doctorRespository.save(doctor));
    }

    @Operation(summary = "Actualizar doctor")
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        return doctorRespository.findById(id)
                .map(doctor -> {
                    Especialidad especialidad = findEspecialidadById(doctorDetails.getEspecialidad().getId());
                    Consultorio consultorio = findConsultorioById(doctorDetails.getConsultorio().getId());

                    doctor.setNombre(doctorDetails.getNombre());
                    doctor.setApellido(doctorDetails.getApellido());
                    doctor.setNumeroLicencia(doctorDetails.getNumeroLicencia());
                    doctor.setHorarioAtencion(doctorDetails.getHorarioAtencion());
                    doctor.setEspecialidad(especialidad);
                    doctor.setConsultorio(consultorio);

                    return ResponseEntity.ok(doctorRespository.save(doctor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar doctor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable Long id) {
        return doctorRespository.findById(id)
                .map(doctor -> {
                    doctorRespository.delete(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private Especialidad findEspecialidadById(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    }

    private Consultorio findConsultorioById(Long id) {
        return consultorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
    }
}
