package com.clinica.clinica.infrastructure.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id; // ✅
import java.time.LocalDateTime;

@Entity
@Table(name= "citas")
public class Citas {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    
    @ManyToOne
    @JoinColumn(name="paciente_id", nullable=false)
    Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name="medico_id", nullable=false)
    @JsonBackReference        
    Medico medico;
    
    LocalDateTime fechaCita;
    String estado, motivo, orderId;

    public Citas() {
    }

    public Citas(Paciente paciente, Medico medico, LocalDateTime fechaCita, String estado, String motivo, String orderId) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaCita = fechaCita;
        this.estado = estado;
        this.motivo = motivo;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    
}
