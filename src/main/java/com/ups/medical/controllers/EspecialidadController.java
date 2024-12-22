package com.ups.medical.controllers;

import com.ups.medical.models.Especialidad;
import com.ups.medical.repositories.EspecialidadRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con Especialidad.
 */
@RestController
@RequestMapping("/api/especialidad")
@Tag(name = "Especialidades Médicas", description = "API para la gestión de especialidades médicas")
public class EspecialidadController {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Operation(summary = "Obtener todas las especialidades",
               description = "Retorna una lista de todas las especialidades médicas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de especialidades médicas recuperada exitosamente")
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Operation(summary = "Obtener especialidad por ID",
               description = "Retorna una especialidad médica específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad médica encontrada"),
        @ApiResponse(responseCode = "404", description = "Especialidad médica no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> getEspecialidadById(@PathVariable Long id) {
        return especialidadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva especialidad médica",
               description = "Registra una nueva especialidad médica en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad médica creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la especialidad médica inválidos")
    })
    @PostMapping
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    @Operation(summary = "Actualizar especialidad médica",
               description = "Actualiza la información de una especialidad médica existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad médica actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Especialidad médica no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de la especialidad médica inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(@PathVariable Long id, @RequestBody Especialidad especialidadDetails) {
        return especialidadRepository.findById(id)
                .map(especialidad -> {
                    // Actualiza los campos necesarios
                    especialidad.setNombre(especialidadDetails.getNombre());
                    return ResponseEntity.ok(especialidadRepository.save(especialidad));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar especialidad médica",
               description = "Elimina una especialidad médica del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Especialidad médica eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Especialidad médica no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEspecialidad(@PathVariable Long id) {
        return especialidadRepository.findById(id)
                .map(especialidad -> {
                    especialidadRepository.delete(especialidad);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
