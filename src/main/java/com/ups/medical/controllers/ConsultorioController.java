package com.ups.medical.controllers;

import com.ups.medical.models.Consultorio;
import com.ups.medical.repositories.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/controlador")
public class ConsultorioController {

    @Autowired
    private ConsultRepository consultRepository;

    // Obtener todas las citas
    @GetMapping
    public List<Consultorio> getAllConsultorios(){
        return consultRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> getConsultorioById(@PathVariable Long id){
        return consultRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Consultorio createConsultorio(@RequestBody Consultorio consultorio){
        return consultRepository.save(consultorio);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> updateConsultorio(@PathVariable Long id, @RequestBody Consultorio consultorioDetails){
        return consultRepository.findById(id)
        .map(consultorio -> {
            consultorio.setNombre(consultorioDetails.getNombre());
            return ResponseEntity.ok(consultRepository.save(consultorio));
        })
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar
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

