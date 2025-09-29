/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RequestDTO;

/**
 *
 * @author mr587
 */
public class MedicoDTO {
    String correoInstitucional, contraseña;

    public MedicoDTO() {
    }

    public MedicoDTO(String correoInstitucional, String contraseña) {
        this.correoInstitucional = correoInstitucional;
        this.contraseña = contraseña;
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
    
}
