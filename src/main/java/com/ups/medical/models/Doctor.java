package com.ups.medical.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author Torres Dilan
 */

@Entity
@Table(name = "doctors")
public class Doctor extends Usuario {

    private String numeroLicencia;
    private String horarioAtencion;

    @OneToMany(mappedBy = "doctor")
    private List<Cita> citas;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    @ManyToOne
    @JoinColumn(name = "consultorio_id", nullable = false)
    private Consultorio consultorio;

    // Constructores
    public Doctor() {
        super(); // Llama al constructor vacío de Usuario
    }

    public Doctor(
            String nombre, String apellido, String cedula, String telefono, String email, String password, String username,
            String numeroLicencia, String horarioAtencion, Especialidad especialidad, Consultorio consultorio) {
        super(nombre, apellido, cedula, telefono, email, password, username); // Inicializa atributos de Usuario
        this.numeroLicencia = numeroLicencia;
        this.horarioAtencion = horarioAtencion;
        this.especialidad = especialidad;
        this.consultorio = consultorio;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }
         
}
