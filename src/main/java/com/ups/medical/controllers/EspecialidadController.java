package com.ups.medical.controllers;

import com.ups.medical.models.Especialidad;
import com.ups.medical.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con Especialidad.
 */
@RestController
@RequestMapping("/api/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Obtener todas las especialidades
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    // Obtener una especialidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> getEspecialidadById(@PathVariable Long id) {
        return especialidadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva especialidad
    @PostMapping
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    // Actualizar una especialidad existente
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

    // Eliminar una especialidad
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
