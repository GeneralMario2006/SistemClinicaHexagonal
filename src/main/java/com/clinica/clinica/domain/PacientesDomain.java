/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain;
import java.util.Date;

public class PacientesDomain {
    Long id;
    String nombre;
    String correo;
    Long dui;    
    Date fechaNacimiento;
    Long telefono;
    String contraseña;
    String rol;

    public PacientesDomain(Long id, String nombre, String correo, Long dui, Date fechaNacimiento, Long telefono, String contraseña, String rol) {
        this.id=id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña= contraseña;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.rol= rol;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getDui() {
        return dui;
    }

    public void setDui(Long dui) {
        this.dui = dui;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
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
    
    

   
}
