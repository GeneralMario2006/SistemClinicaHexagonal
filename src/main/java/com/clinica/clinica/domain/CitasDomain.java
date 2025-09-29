package com.clinica.clinica.domain;

import java.time.LocalDateTime;


public class CitasDomain {
private Long id;
    PacientesDomain paciente;
    MedicoDomain medico;
    LocalDateTime fechaCita;
    String estado, motivo, orderId;

    public CitasDomain(PacientesDomain paciente, MedicoDomain medico, LocalDateTime fechaCita, String estado, String motivo, String orderId) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaCita = fechaCita;
        this.estado = estado;
        this.motivo = motivo;
        this.orderId= orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PacientesDomain getPaciente() {
        return paciente;
    }

    public void setPaciente(PacientesDomain paciente) {
        this.paciente = paciente;
    }

    public MedicoDomain getMedico() {
        return medico;
    }

    public void setMedico(MedicoDomain medico) {
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
