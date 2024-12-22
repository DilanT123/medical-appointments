package com.ups.medical.controllers;

import com.ups.medical.models.RecetaMedica;
import com.ups.medical.repositories.RecMedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con RecetaMedica.
 */
@RestController
@RequestMapping("/api/recetas-medicas")
public class RecMedController {

    @Autowired
    private RecMedRepository recMedRepository;

    // Obtener todas las recetas médicas
    @GetMapping
    public List<RecetaMedica> getAllRecetasMedicas() {
        return recMedRepository.findAll();
    }

    // Obtener una receta médica por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedica> getRecetaMedicaById(@PathVariable Long id) {
        return recMedRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva receta médica
    @PostMapping
    public RecetaMedica createRecetaMedica(@RequestBody RecetaMedica recetaMedica) {
        return recMedRepository.save(recetaMedica);
    }

    // Actualizar una receta médica existente
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

    // Eliminar una receta médica
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
