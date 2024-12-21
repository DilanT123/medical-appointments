package com.ups.medical.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Torres
 */

@Entity
@Table(name = "historiales_medicos")
class HistorialMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String diagnostico;
    
    @Column
    private String prescripcion;
    
    @Column
    private String observaciones;
    
    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;
    
    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Constructores

    public HistorialMedico() {}

    public HistorialMedico(Long id, String diagnostico, String prescripcion, String observaciones, Cita cita, Paciente paciente) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.prescripcion = prescripcion;
        this.observaciones = observaciones;
        this.cita = cita;
        this.paciente = paciente;
    }

    public HistorialMedico(String diagnostico, String prescripcion, String observaciones, Cita cita, Paciente paciente) {
        this.diagnostico = diagnostico;
        this.prescripcion = prescripcion;
        this.observaciones = observaciones;
        this.cita = cita;
        this.paciente = paciente;
    }
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
}
