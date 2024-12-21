package com.ups.medical.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author Torres Dilan
 */

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDoctor;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false)
    private String especialidad;
    
    @Column(nullable = false)
    private String numeroLicencia;

    @Column(nullable = false)
    private String horarioAtencion;
    
    @OneToMany(mappedBy = "doctor")
    private List<Cita> citas;
    
    //Constructores

    public Doctor() {}

    public Doctor(int idDoctor, String nombre, String apellido, String especialidad, String numeroLicencia, String horarioAtencion, List<Cita> citas) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
        this.horarioAtencion = horarioAtencion;
        this.citas = citas;
    }
    
    // Getters and Setters

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
    
}
