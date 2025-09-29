package com.clinica.clinica.infrastructure.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Id; // ✅

@Entity
@Table(name= "Paciente")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    String nombre;
    
    @Column(name= "correo", unique=true)
    String correo;
    
    @Column(name= "DUI", unique=true)
    Long dui;
    
    Date fechaNacimiento;
    
    @Column(name= "celular")
    Long telefono;
    
    @OneToMany(mappedBy="paciente",  fetch = FetchType.LAZY)
       @JsonIgnore
   List<Citas>citas;
    
    @OneToMany(mappedBy= "paciente",  fetch = FetchType.LAZY)
            @JsonIgnore
    List<Mensajes> mensajes;
    String contraseña;
    
    String rol;
    
    public Paciente() {
    }

    public Paciente(String nombre, String correo, Long dui, Date fechaNacimiento, Long telefono, String contraseña, String rol) {
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

    

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
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
