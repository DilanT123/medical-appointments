package com.ups.medical.repositories;

import com.ups.medical.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Repositorio para manejar las operaciones CRUD de Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para encontrar usuarios por su email
    Usuario findByEmail(String email);
    //
    Optional<Usuario> findByUsername(String username);
}

    
