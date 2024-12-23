/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.medical.services;

/**
 *
 * @author Fernando
 */
import com.ups.medical.models.Usuario;
import com.ups.medical.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticarUsuario(String username, String password) throws Exception {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verificar contraseña
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Generar token (puedes implementar JWT aquí)
        return "fake-token"; // Cambia por tu lógica de generación de tokens
    }

}
