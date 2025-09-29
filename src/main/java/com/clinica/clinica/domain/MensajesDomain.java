package com.clinica.clinica.domain;

public class MensajesDomain {
    public Long id;

    public PacientesDomain paciente;
    public MedicoDomain medico;
    public String contenido;
    public String remitente;

    public MensajesDomain() {
    }

    public MensajesDomain(PacientesDomain paciente, MedicoDomain medico, String contenido, String remitente) {
        this.paciente = paciente;
        this.medico = medico;
        this.contenido = contenido;
        this.remitente = remitente;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }
}
