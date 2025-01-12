package com.ups.medical.security;

import com.ups.medical.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {
        "/swagger-ui/**",
        "/api-docs/**",
        "/swagger-ui.html",
        "/api/usuarios/**",
        "/api/controlador/**",
        "/api/cita/**",
        "/api/doctor/**",
        "/api/especialidad/**",
        "/api/historialMedico/**",
        "/api/pacientes/**", // Incluye todas las rutas de pacientes
        "/api/pacientes/**/**" // Rutas anidadas o dinámicas (si aplica)
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider, UsuarioService usuarioService) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .authorizeRequests(auth -> auth
                .requestMatchers(PUBLIC_URLS).permitAll() // Permitir acceso sin autenticación
                .anyRequest().authenticated() // Requiere autenticación para el resto
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, usuarioService), UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // Permitir todos los orígenes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));  // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));  // Permitir todos los encabezados
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(false);  // No permitir credenciales
        configuration.setMaxAge(3600L);  // Cache para las solicitudes CORS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Configuración CORS para todas las rutas
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Codificación BCrypt para contraseñas
    }
}
