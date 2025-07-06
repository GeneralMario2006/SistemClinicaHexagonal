/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RequestDTO;


import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author mr587
 */
public class CitaDTO {
    Long dui, idMedico;
    
    
    LocalDateTime fechaCita;
    
    String motivo;

    public CitaDTO() {
    }

    public CitaDTO(Long dui, Long idMedico, LocalDateTime fechaCita, String motivo) {
        this.dui = dui;
        this.idMedico = idMedico;
        this.fechaCita = fechaCita;
        this.motivo = motivo;
    }

    public Long getDui() {
        return dui;
    }

    public void setDui(Long dui) {
        this.dui = dui;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico= idMedico;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    
}
