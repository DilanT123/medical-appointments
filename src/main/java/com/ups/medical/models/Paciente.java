package com.ups.medical.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Torres
 */

@Entity
@Table(name = "pacientes")
public class Paciente extends Usuario {

    private String genero;
    private LocalDate fechaNacimiento;
    private String tipoSangre;

    @OneToMany(mappedBy = "paciente")
    private List<Cita> citas;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistorialMedico historialMedico;

    // Constructores
    public Paciente() {
        super(); // Llama al constructor vacío de Usuario
    }

    public Paciente(
            String nombre, String apellido, String cedula, String telefono, String email, String password, String username,
            String genero, LocalDate fechaNacimiento, String tipoSangre) {
        super(nombre, apellido, cedula, telefono, email, password, username); // Inicializa atributos de Usuario
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
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

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }
    
    
}
