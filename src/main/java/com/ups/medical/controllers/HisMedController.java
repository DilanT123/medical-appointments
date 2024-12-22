package com.ups.medical.controllers;

import com.ups.medical.models.HistorialMedico;
import com.ups.medical.repositories.HisMedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con HistorialMedico.
 */
@RestController
@RequestMapping("/api/historialMedico")
public class HisMedController {

    @Autowired
    private HisMedRepository hisMedRepository;

    // Obtener todos los historiales médicos
    @GetMapping
    public List<HistorialMedico> getAllHistorialMedico() {
        return hisMedRepository.findAll();
    }

    // Obtener un historial médico por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> getHistorialMedicoById(@PathVariable Long id) {
        return hisMedRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo historial médico
    @PostMapping
    public HistorialMedico createHistorialMedico(@RequestBody HistorialMedico historialMedico) {
        return hisMedRepository.save(historialMedico);
    }

    // Actualizar un historial médico existente
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

    // Eliminar un historial médico
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
