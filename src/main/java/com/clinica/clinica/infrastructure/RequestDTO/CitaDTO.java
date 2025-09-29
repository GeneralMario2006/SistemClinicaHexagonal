/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RequestDTO;


import java.time.LocalDateTime;


/**
 *
 * @author mr587
 */
public class CitaDTO {
    Long dui;
    String nombreMedico;
    
    
    LocalDateTime fechaCita;
    
    String motivo;

    public CitaDTO() {
    }

    public CitaDTO(Long dui, String nombreMedico, LocalDateTime fechaCita, String motivo) {
        this.dui = dui;
        this.nombreMedico = nombreMedico;
        this.fechaCita = fechaCita;
        this.motivo = motivo;
        
    }

    public Long getDui() {
        return dui;
    }

    public void setDui(Long dui) {
        this.dui = dui;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
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
