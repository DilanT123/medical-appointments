package com.ups.medical;

import com.ups.medical.controllers.CitaController;
import com.ups.medical.models.Cita;
import com.ups.medical.repositories.CitaRepository;
import com.ups.medical.repositories.DoctorRespository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CitaController.class)
public class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaRepository citaRepository;

    @MockBean
    private DoctorRespository doctorRespository;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetAllCitas() throws Exception {
        // Mockear datos de prueba
        Cita cita = new Cita();
        cita.setId(1L);
        cita.setFechaHora(LocalDateTime.now());
        cita.setMotivo("Consulta general");

        Mockito.when(citaRepository.findAll()).thenReturn(Collections.singletonList(cita));

// Ejecutar la petición GET y verificar la respuesta
    mockMvc.perform(get("/api/cita")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].motivo").value("Consulta general"))
            .andExpect(result -> {
                String content = result.getResponse().getContentAsString();
                assertThat(content).contains("Consulta general");
            });
}

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetCitaById_NotFound() throws Exception {
        Mockito.when(citaRepository.findById(1L)).thenReturn(Optional.empty());

        // Probar que el endpoint devuelve 404 cuando no encuentra la cita
        mockMvc.perform(get("/api/cita/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    
}
