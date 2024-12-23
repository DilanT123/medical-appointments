package com.ups.medical.security;

/**
 *
 * @author Torres
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactiva CSRF si no es necesario
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll() // Swagger accesible sin autenticación
                .requestMatchers("/api/usuarios/**").permitAll() // Acceso libre a los endpoints de registro de usuario
                .requestMatchers("/api/controlador/**").permitAll() // Permite acceso sin autenticación al endpoint del controlador
                .requestMatchers("/api/cita/**").permitAll()  // Permite acceso a todas las rutas de /api/cita
                .requestMatchers("/api/doctor/**").permitAll()  // Permite acceso a todas las rutas de /api/doctor
                .requestMatchers("/api/especialidad/**").permitAll()  // Permite acceso a todas las rutas de /api/especialidad
                .requestMatchers("/api/historialMedico/**").permitAll()  // Permite acceso a todas las rutas de /api/historialMedico
                .requestMatchers("/api/pacientes/**").permitAll()  // Permite acceso a todas las rutas de /api/pacientes
                .requestMatchers("/api/recetas-medicas/**").permitAll()  // Permite acceso a todas las rutas de /api/recetas-medicas
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otro endpoint
            )
            .cors().and(); 

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
