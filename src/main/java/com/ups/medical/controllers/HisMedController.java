package com.ups.medical.controllers;

import com.ups.medical.models.HistorialMedico;
import com.ups.medical.repositories.HisMedRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con HistorialMedico.
 */
@RestController
@RequestMapping("/api/historialMedico")
@Tag(name = "Historial Médico", description = "API para la gestión de historiales médicos")
public class HisMedController {

    @Autowired
    private HisMedRepository hisMedRepository;

    @Operation(summary = "Obtener todos los historiales médicos",
           description = "Retorna una lista de todos los historiales médicos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de historiales médicos recuperada exitosamente")
    @GetMapping
    public List<HistorialMedico> getAllHistorialMedico() {
        return hisMedRepository.findAll();
    }

    @Operation(summary = "Obtener historial médico por ID",
           description = "Retorna un historial médico específico basado en su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial médico encontrado"),
    @ApiResponse(responseCode = "404", description = "Historial médico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> getHistorialMedicoById(@PathVariable Long id) {
        return hisMedRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo historial médico",
           description = "Registra un nuevo historial médico en el sistema")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial médico creado exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos del historial médico inválidos")    
    })
    @PostMapping
    public HistorialMedico createHistorialMedico(@RequestBody HistorialMedico historialMedico) {
        return hisMedRepository.save(historialMedico);
    }

    @Operation(summary = "Actualizar historial médico",
           description = "Actualiza la información de un historial médico existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial médico actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Historial médico no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del historial médico inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> updateHistorialMedico(@PathVariable Long id, @RequestBody HistorialMedico historialMedicoDetails) {
        return hisMedRepository.findById(id)
                .map(historialMedico -> {
                    // Actualiza los campos necesarios
                    historialMedico.setDiagnostico(historialMedicoDetails.getDiagnostico());
                    historialMedico.setPrescripcion(historialMedicoDetails.getPrescripcion());
                    historialMedico.setObservaciones(historialMedicoDetails.getObservaciones());
                    historialMedico.setCita(historialMedicoDetails.getCita());
                    historialMedico.setPaciente(historialMedicoDetails.getPaciente());
                    return ResponseEntity.ok(hisMedRepository.save(historialMedico));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar historial médico",
           description = "Elimina un historial médico del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Historial médico eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Historial médico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHistorialMedico(@PathVariable Long id) {
        return hisMedRepository.findById(id)
                .map(historialMedico -> {
                    hisMedRepository.delete(historialMedico);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
