package com.ups.medical.controllers;

import com.ups.medical.models.Consultorio;
import com.ups.medical.repositories.ConsultRepository;
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
@RequestMapping("/api/controlador")
@Tag(name = "Consultorio", description = "API para la gestión de consultorios del sistema médico")
public class ConsultorioController {

    @Autowired
    private ConsultRepository consultRepository;

    @Operation(summary = "Obtener todos los consultorios",
               description = "Retorna una lista de todos los consultorios registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de consultorios recuperada exitosamente")
    @GetMapping
    public List<Consultorio> getAllConsultorios(){
        return consultRepository.findAll();
    }
    
    @Operation(summary = "Obtener consultorio por ID",
               description = "Retorna un consultorio específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultorio encontrado"),
        @ApiResponse(responseCode = "404", description = "Consultorio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> getConsultorioById(@PathVariable Long id){
        return consultRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Crear nuevo consultorio",
               description = "Registra un nuevo consultorio en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultorio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del consultorio inválidos")
    })
    @PostMapping
    public Consultorio createConsultorio(@RequestBody Consultorio consultorio){
        return consultRepository.save(consultorio);
    }

    @Operation(summary = "Actualizar consultorio",
               description = "Actualiza la información de un consultorio existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultorio actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Consultorio no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del consultorio inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> updateConsultorio(@PathVariable Long id, @RequestBody Consultorio consultorioDetails){
        return consultRepository.findById(id)
        .map(consultorio -> {
            consultorio.setNombre(consultorioDetails.getNombre());
            return ResponseEntity.ok(consultRepository.save(consultorio));
        })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar consultorio",
               description = "Elimina un consultorio del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Consultorio eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Consultorio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConsultorio(@PathVariable Long id){
        return consultRepository.findById(id)
                .map(consultorio -> {
                    consultRepository.delete(consultorio);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

