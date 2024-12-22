package com.ups.medical.controllers;

import com.ups.medical.models.Paciente;
import com.ups.medical.repositories.PacienteRepository;
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
 * Controlador para manejar las solicitudes relacionadas con Paciente.
 */
@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Paciente", description = "API para la gestión de pacientes del sistema médico")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Operation(summary = "Obtener todos los pacientes", 
               description = "Retorna una lista de todos los pacientes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada exitosamente")
    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @Operation(summary = "Obtener paciente por ID",
               description = "Retorna un paciente específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(
            @Parameter(description = "ID del paciente", required = true)
            @PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo paciente",
               description = "Registra un nuevo paciente en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del paciente inválidos")
    })
    @PostMapping
    public Paciente createPaciente(
            @Parameter(description = "Datos del paciente a crear", required = true)
            @RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Operation(summary = "Actualizar paciente",
               description = "Actualiza la información de un paciente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del paciente inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
            @Parameter(description = "ID del paciente", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del paciente", required = true)
            @RequestBody Paciente pacienteDetails) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setGenero(pacienteDetails.getGenero());
                    paciente.setFechaNacimiento(pacienteDetails.getFechaNacimiento());
                    paciente.setTipoSangre(pacienteDetails.getTipoSangre());
                    paciente.setCitas(pacienteDetails.getCitas());
                    paciente.setHistorialMedico(pacienteDetails.getHistorialMedico());
                    return ResponseEntity.ok(pacienteRepository.save(paciente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar paciente",
               description = "Elimina un paciente del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaciente(
            @Parameter(description = "ID del paciente a eliminar", required = true)
            @PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    pacienteRepository.delete(paciente);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
