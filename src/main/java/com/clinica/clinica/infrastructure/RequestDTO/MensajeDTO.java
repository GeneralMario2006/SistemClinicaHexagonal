/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RequestDTO;

/**
 *
 * @author mr587
 */
public class MensajeDTO {
    
    private String contenido;
    
    public String correo;

    public MensajeDTO() {
    }

    public MensajeDTO( String contenido, String correo) {
        this.contenido = contenido;
       
        this.correo= correo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

 

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    
}
