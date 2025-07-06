package com.clinica.clinica.infrastructure.Entitys;

import com.clinica.clinica.infrastructure.Entitys.Paciente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 *
 * @author mr587
 */
@Entity
public class Mensajes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name="id_medico")
    private Medico medico;

    private String contenido;

    private String remitente;

    public Mensajes() {
    }

    public Mensajes(Paciente paciente, Medico medico, String contenido, String remitente) {
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
