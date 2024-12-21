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
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String genero;
    
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column
    private String tipoSangre;
    
    @OneToMany(mappedBy = "paciente")
    private List<Cita> citas;
    
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistorialMedico historialMedico;
    
    // Constructores
    public Paciente() {}

    public Paciente(String genero, LocalDate fechaNacimiento, String tipoSangre) {
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
    }
       
    public Paciente(Long id, String genero, LocalDate fechaNacimiento, String tipoSangre, List<Cita> citas, HistorialMedico historialMedico) {
        this.id = id;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
        this.citas = citas;
        this.historialMedico = historialMedico;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
