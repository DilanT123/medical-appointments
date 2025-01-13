package com.ups.medical.controllers;

import com.ups.medical.models.Rol;
import com.ups.medical.repositories.RolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol")
@Tag(name = "Rol", description = "API para la gestión de roles del sistema médico")
public class RolController {
    @Autowired
    private RolRepository RolRepository;
    @Operation(summary = "Obtener todas los roles",
            description = "Retorna una lista de todos los roles registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de roles recuperados exitosamente")
    @GetMapping
    public List<Rol> getAllRoles(){
        return RolRepository.findAll();
    }
    @Operation(summary = "Obtener rol por ID",
            description = "Retorna un rol específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrada¿o")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        return RolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Crear nuevo rol",
            description = "Registra un nuevo rol en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Rol createRol(@RequestBody Rol rol) {
        return RolRepository.save(rol);
    }
    @Operation(summary = "Actualizar rol",
            description = "Actualiza la información del rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rolDetails) {
        return RolRepository.findById(id)
                .map(rol -> {
                    rol.setNombre(rolDetails.getNombre());
                    return ResponseEntity.ok(RolRepository.save(rol));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Eliminar rol",
            description = "Elimina un rol del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRol(@PathVariable Long id) {
        return RolRepository.findById(id)
                .map(rol -> {
                    RolRepository.delete(rol);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}