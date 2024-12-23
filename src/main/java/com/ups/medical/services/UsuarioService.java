package com.ups.medical.services;

/**
 *
 * @author Fernando
 */
import com.ups.medical.models.Usuario;
import com.ups.medical.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "miClaveSecreta";  // Usa una clave secreta segura

    // Método para autenticar al usuario y generar el token JWT
    public String autenticarUsuario(String username, String password) throws Exception {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verificar contraseña
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Generar el token JWT
        return generarToken(usuario);
    }

    // Método privado para generar el token JWT
    private String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiración de 1 día
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Método para obtener un usuario por su nombre de usuario
    public Optional<Usuario> obtenerUsuarioPorNombre(String username) {
        return Optional.ofNullable(usuarioRepository.findByUsername(username).orElse(null));
    }
}