package com.clinica.clinica.infrastructure.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import jakarta.persistence.Id; // ✅
import jakarta.persistence.Lob;
import java.time.LocalTime;

@Entity
@Table(name= "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    
    String nombre, especialidad;
    
    @Column(name="correoInstitucional", unique=true)
    String correoInstitucional;
    
    @OneToMany(mappedBy="medico")
    @JsonIgnore          
    List<Citas> citas;
    
    @OneToMany(mappedBy="medico",  fetch = FetchType.LAZY)
    @JsonIgnore        
    List<Mensajes> mensajes;
    
    LocalTime horarioInicio, horarioCierre;
    
    public String contraseña;
    


    

    String rol;

    public Medico() {
    }

    public Medico(String correoInstitucional, String nombre, String especialidad, String contraseña, LocalTime horarioInicio, LocalTime horarioCierre, String rol) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.contraseña= contraseña;
        this.correoInstitucional= correoInstitucional;
        this.horarioInicio= horarioInicio;
        this.horarioCierre= horarioCierre;
        this.rol=rol;
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

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
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

    public List<Mensajes> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensajes> mensajes) {
        this.mensajes = mensajes;
    }

    
    
}
