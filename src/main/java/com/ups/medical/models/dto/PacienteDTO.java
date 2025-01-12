package com.ups.medical.models.dto;

import java.time.LocalDate;

/**
 *
 * @author Torres
 */
public class PacienteDTO {

    private String genero;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private Long usuarioId;

    public PacienteDTO() {
    }

    public PacienteDTO(String genero, LocalDate fechaNacimiento, String tipoSangre, Long usuarioId) {
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
        this.usuarioId = usuarioId;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

}
