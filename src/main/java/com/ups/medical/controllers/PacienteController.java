package com.ups.medical.controllers;

import com.ups.medical.models.Paciente;
import com.ups.medical.models.Usuario;
import com.ups.medical.models.dto.PacienteDTO;
import com.ups.medical.repositories.PacienteRepository;
import com.ups.medical.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes relacionadas con Paciente.
 */
@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Paciente", description = "API para la gestión de pacientes del sistema médico")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtener todos los pacientes registrados en el sistema.
     *
     * @return Lista de pacientes.
     */
    @Operation(summary = "Obtener todos los pacientes", description = "Retorna una lista de todos los pacientes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada exitosamente")
    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    /**
     * Obtener un paciente específico por su ID.
     *
     * @param id ID del paciente.
     * @return Paciente encontrado o error 404 si no existe.
     */
    @Operation(summary = "Obtener paciente por ID", description = "Retorna un paciente específico basado en su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(
            @Parameter(description = "ID del paciente", required = true) @PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo paciente.
     *
     * @param pacienteDTO Objeto DTO con los datos del paciente a crear.
     * @return Paciente creado o error 400 si los datos son inválidos.
     */
    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del paciente inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> createPaciente(
            @RequestBody PacienteDTO pacienteDTO) {
        // Validar que el ID de usuario no sea nulo
        if (pacienteDTO.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("El campo usuarioId es obligatorio.");
        }

        // Buscar usuario por ID
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(pacienteDTO.getUsuarioId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario con ID " + pacienteDTO.getUsuarioId() + " no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        // Crear paciente a partir del DTO
        Paciente paciente = new Paciente();
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setTipoSangre(pacienteDTO.getTipoSangre());
        paciente.setUsuario(usuario);

        // Guardar paciente en la base de datos
        try {
            Paciente savedPaciente = pacienteRepository.save(paciente);
            return ResponseEntity.status(201).body(savedPaciente);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar el paciente: " + e.getMessage());
        }
    }

    /**
     * Actualizar la información de un paciente existente.
     *
     * @param id ID del paciente a actualizar.
     * @param pacienteDetails Nuevos datos del paciente.
     * @return Paciente actualizado o error 404 si no existe.
     */
    @Operation(summary = "Actualizar paciente", description = "Actualiza la información de un paciente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del paciente inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
            @Parameter(description = "ID del paciente", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del paciente", required = true) @RequestBody Paciente pacienteDetails) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setGenero(pacienteDetails.getGenero());
                    paciente.setFechaNacimiento(pacienteDetails.getFechaNacimiento());
                    paciente.setTipoSangre(pacienteDetails.getTipoSangre());
                    return ResponseEntity.ok(pacienteRepository.save(paciente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un paciente por su ID.
     *
     * @param id ID del paciente a eliminar.
     * @return Respuesta con código 204 si se elimina o 404 si no existe.
     */
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaciente(
            @Parameter(description = "ID del paciente a eliminar", required = true) @PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    pacienteRepository.delete(paciente);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
