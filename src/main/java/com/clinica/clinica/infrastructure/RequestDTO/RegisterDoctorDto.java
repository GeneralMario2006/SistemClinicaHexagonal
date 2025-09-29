/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RequestDTO;

import java.time.LocalTime;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mr587
 */
public class RegisterDoctorDto {
    String nombre, especialidad;
    String correoInstitucional;
    
    LocalTime horarioInicio, horarioCierre;
    
    public String contraseña;

    public RegisterDoctorDto(String nombre, String especialidad, String correoInstitucional, LocalTime horarioInicio, LocalTime horarioCierre, String contraseña) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.correoInstitucional = correoInstitucional;
        this.horarioInicio = horarioInicio;
        this.horarioCierre = horarioCierre;
        this.contraseña = contraseña;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(LocalTime horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

 
}
