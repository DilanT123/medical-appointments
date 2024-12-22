package com.ups.medical.controllers;

import com.ups.medical.models.RecetaMedica;
import com.ups.medical.repositories.RecMedRepository;
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
 * Controlador para manejar las solicitudes relacionadas con RecetaMedica.
 */
@RestController
@RequestMapping("/api/recetas-medicas")
@Tag(name = "Receta Médica", description = "API para la gestión de recetas médicas del sistema médico")
public class RecMedController {

    @Autowired
    private RecMedRepository recMedRepository;

    @Operation(summary = "Obtener todas las recetas médicas",
               description = "Retorna una lista de todas las recetas médicas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de recetas médicas recuperada exitosamente")
    @GetMapping
    public List<RecetaMedica> getAllRecetasMedicas() {
        return recMedRepository.findAll();
    }

    @Operation(summary = "Obtener receta médica por ID",
               description = "Retorna una receta médica específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta médica encontrada"),
        @ApiResponse(responseCode = "404", description = "Receta médica no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedica> getRecetaMedicaById(@PathVariable Long id) {
        return recMedRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva receta médica",
               description = "Registra una nueva receta médica en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta médica creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la receta médica inválidos")
    })
    @PostMapping
    public RecetaMedica createRecetaMedica(@RequestBody RecetaMedica recetaMedica) {
        return recMedRepository.save(recetaMedica);
    }

    @Operation(summary = "Actualizar receta médica",
               description = "Actualiza la información de una receta médica existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta médica actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de la receta médica inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecetaMedica> updateRecetaMedica(@PathVariable Long id, @RequestBody RecetaMedica recetaDetails) {
        return recMedRepository.findById(id)
                .map(recetaMedica -> {
                    recetaMedica.setDescripcion(recetaDetails.getDescripcion());
                    recetaMedica.setCita(recetaDetails.getCita());
                    return ResponseEntity.ok(recMedRepository.save(recetaMedica));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar receta médica",
               description = "Elimina una receta médica del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Receta médica eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Receta médica no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecetaMedica(@PathVariable Long id) {
        return recMedRepository.findById(id)
                .map(recetaMedica -> {
                    recMedRepository.delete(recetaMedica);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
