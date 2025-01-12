package com.ups.medical.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author Torres, Maddiekc
 */

@Entity
@Table(name = "citas")
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(nullable = false)
    private String motivo;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL)
    private HistorialMedico historialMedico;
    
    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL)
    private RecetaMedica recetaMedica;

    // Enum para el estado de la cita
    public enum EstadoCita {
        PROGRAMADA,
        CONFIRMADA,
        CANCELADA,
        ATENDIDA
    }
    
    // Constructores
    public Cita() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public RecetaMedica getRecetaMedica() {
        return recetaMedica;
    }

    public void setRecetaMedica(RecetaMedica recetaMedica) {
        this.recetaMedica = recetaMedica;
    }
       
}
