package com.ups.medical.controllers;

import com.ups.medical.models.Cita;
import com.ups.medical.models.Doctor;
import com.ups.medical.models.dto.CitaDTO;
import com.ups.medical.repositories.CitaRepository;
import com.ups.medical.repositories.DoctorRespository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 *
 * @author Maddiekc, DilanT123
 */
@RestController
@RequestMapping("/api/cita")
@Tag(name = "Citas Médicas", description = "API para la gestión de citas médicas")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private DoctorRespository doctorRespository;
    
    @Operation(summary = "Obtener todas las citas médicas",
            description = "Retorna una lista de todas las citas médicas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de citas médicas recuperada exitosamente")
    @GetMapping
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    @Operation(summary = "Obtener cita médica por ID",
            description = "Retorna una cita médica específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cita médica encontrada"),
        @ApiResponse(responseCode = "404", description = "Cita médica no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        return citaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva cita médica",
            description = "Registra una nueva cita médica en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cita médica creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la cita médica inválidos"),
        @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @PostMapping("/crearCita")
    public ResponseEntity<String> createCita(@RequestBody CitaDTO citaDTO) {
        // Validar el ID del doctor
        if (citaDTO.getDoctorId() == null) {
            return ResponseEntity.badRequest().body("El ID del doctor no puede ser nulo");
        }

        // Buscar el doctor en la base de datos
        Doctor doctor = doctorRespository.findById(citaDTO.getDoctorId())   
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        
        // Crear la cita con la información proporcionada
        Cita cita = new Cita();
        cita.setDoctor(doctor);
        cita.setFechaHora(citaDTO.getFechaHora());
        cita.setMotivo(citaDTO.getMotivo());
        // Guardar la cita en la base de datos
        citaRepository.save(cita);
        return ResponseEntity.ok("Cita creada correctamente");
    }

    @Operation(summary = "Actualizar cita médica",
            description = "Actualiza la información de una cita médica existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cita médica actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cita médica no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de la cita médica inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        return citaRepository.findById(id)
                .map(cita -> {
                    // Actualiza los campos necesarios
                    cita.setFechaHora(citaDetails.getFechaHora()); // Ejemplo
                    cita.setMotivo(citaDetails.getMotivo()); // Ejemplo
                    return ResponseEntity.ok(citaRepository.save(cita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar cita médica",
            description = "Elimina una cita médica del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cita médica eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cita médica no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCita(@PathVariable Long id) {
        return citaRepository.findById(id)
                .map(cita -> {
                    citaRepository.delete(cita);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
