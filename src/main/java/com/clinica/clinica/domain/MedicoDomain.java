/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain;

import java.time.LocalTime;

/**
 *
 * @author mr587
 */
public class MedicoDomain {

    Long id;
    String nombre, especialidad;
    String correoInstitucional;
    LocalTime horarioInicio, horarioCierre;
    public String contraseña;

    String rol;

    public MedicoDomain(Long id, String correoInstitucional, String nombre, String especialidad, String contraseña, LocalTime horarioInicio, LocalTime horarioCierre, String rol) {
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
        this.contraseña = contraseña;
        this.correoInstitucional = correoInstitucional;
        this.horarioInicio = horarioInicio;
        this.horarioCierre = horarioCierre;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

}
