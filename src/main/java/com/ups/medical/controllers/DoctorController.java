package com.ups.medical.controllers;

import com.ups.medical.models.Consultorio;
import com.ups.medical.models.Doctor;
import com.ups.medical.models.Especialidad;
import com.ups.medical.repositories.ConsultRepository;
import com.ups.medical.repositories.DoctorRespository;
import com.ups.medical.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Doctor", description = "API para la gestión de doctores del sistema médico")
public class DoctorController {

    @Autowired
    private DoctorRespository doctorRespository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private ConsultRepository consultorioRepository;

    @Operation(summary = "Obtener todos los doctores",
            description = "Retorna una lista de todos los doctores registrados en el sistema")
    @GetMapping
    public List<Doctor> getAllDoctor() {
        return doctorRespository.findAll();
    }

    @Operation(summary = "Obtener doctor por ID",
            description = "Retorna un doctor específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @Parameter(description = "ID del doctor", required = true)
            @PathVariable Long id) {
        return doctorRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo doctor",
            description = "Registra un nuevo doctor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del doctor inválidos"),
            @ApiResponse(responseCode = "404", description = "Especialidad o consultorio no encontrados")
    })
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @Parameter(description = "Datos del doctor a crear", required = true)
            @RequestBody Doctor doctorRequest) {

        // Buscar especialidad y consultorio por id
        Especialidad especialidad = especialidadRepository.findById(doctorRequest.getEspecialidad().getId())
                .orElse(null);

        Consultorio consultorio = consultorioRepository.findById(doctorRequest.getConsultorio().getId())
                .orElse(null);

        if (especialidad == null || consultorio == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentran
        }

        // Crear el nuevo doctor con los datos recibidos y las entidades asociadas
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
                consultorio
        );

        // Guardar el doctor en la base de datos
        Doctor savedDoctor = doctorRespository.save(doctor);
        return ResponseEntity.ok(savedDoctor); // Devuelve el doctor creado con un 200
    }

    @Operation(summary = "Actualizar doctor",
            description = "Actualiza la información de un doctor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos del doctor inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @Parameter(description = "ID del doctor", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del doctor", required = true)
            @RequestBody Doctor doctorDetails) {
        return doctorRespository.findById(id)
                .map(doctor -> {
                    doctor.setApellido(doctorDetails.getApellido());
                    doctor.setNombre(doctorDetails.getNombre());
                    doctor.setHorarioAtencion(doctorDetails.getHorarioAtencion());
                    doctor.setNumeroLicencia(doctorDetails.getNumeroLicencia());
                    doctor.setConsultorio(doctorDetails.getConsultorio());
                    doctor.setEspecialidad(doctorDetails.getEspecialidad());
                    return ResponseEntity.ok(doctorRespository.save(doctor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar doctor",
            description = "Elimina un doctor del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoctor(
            @Parameter(description = "ID del doctor a eliminar", required = true)
            @PathVariable Long id) {
        return doctorRespository.findById(id)
                .map(doctor -> {
                    doctorRespository.delete(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}