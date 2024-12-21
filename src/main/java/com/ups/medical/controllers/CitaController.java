package com.ups.medical.controllers;

import com.ups.medical.models.Cita;
import com.ups.medical.repositories.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Maddiekc
 */

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    // Obtener todas las citas
    @GetMapping
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    // Obtener una cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        return citaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva cita
    @PostMapping
    public Cita createCita(@RequestBody Cita cita) {
        return citaRepository.save(cita);
    }

    // Actualizar una cita existente
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

    // Eliminar una cita
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
