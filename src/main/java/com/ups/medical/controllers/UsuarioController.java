package com.ups.medical.controllers;

import com.ups.medical.models.LoginRequest;
import com.ups.medical.models.LoginResponse;
import com.ups.medical.models.Usuario;
import com.ups.medical.repositories.UsuarioRepository;
import com.ups.medical.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * Controlador para manejar las solicitudes relacionadas con Usuario.
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario", description = "API para la gestión de usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Operation(summary = "Obtener todos los usuarios", 
               description = "Retorna una lista de todos los usuarios registrados en el sistema")
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Operation(summary = "Obtener usuario por ID",
               description = "Retorna un usuario basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo usuario",
               description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    @PostMapping
    public Usuario createUsuario(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Operation(summary = "Actualizar usuario",
               description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioDetails.getNombre());
                    usuario.setApellido(usuarioDetails.getApellido());
                    usuario.setCedula(usuarioDetails.getCedula());
                    usuario.setTelefono(usuarioDetails.getTelefono());
                    usuario.setEmail(usuarioDetails.getEmail());
                    usuario.setPassword(usuarioDetails.getPassword());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario",
               description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.delete(usuario);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuario por email",
               description = "Busca un usuario por su dirección de correo electrónico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> getUsuarioByEmail(
            @Parameter(description = "Email del usuario", required = true)
            @RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
     private UsuarioService usuarioService;
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = usuarioService.autenticarUsuario(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse("Login exitoso", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(e.getMessage(), null));
        }
    }
    
    
    
    
    
    
    
    
}
