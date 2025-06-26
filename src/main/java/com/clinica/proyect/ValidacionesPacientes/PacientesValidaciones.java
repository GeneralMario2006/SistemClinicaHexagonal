/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.proyect.ValidacionesPacientes;

import com.clinica.proyect.DTOS.PacienteDTOretorno;
import com.clinica.proyect.Entitys.Paciente;
import com.clinica.proyect.Repositorys.RepositoryPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PacientesValidaciones {
    @Autowired
    RepositoryPaciente repositoryPaciente;
    
    @Autowired
    PasswordEncoder PE;
    
    public boolean ExistYCrearPaciente(Paciente paciente) {
        return repositoryPaciente.findByDui(paciente.getDui()).isPresent();
    }
   
    public void crearPaciente(Paciente paciente) {
       if (paciente.getCorreo()==null || paciente.getDui()==null || paciente.getNombre()==null || paciente.getTelefono()==null) {
           System.out.println("CAMPOS VACÍOS");
           return;
       }
        repositoryPaciente.save(paciente);
        paciente.setContraseña(PE.encode(paciente.getContraseña()));
        paciente.setRol("PACIENTE");
        repositoryPaciente.save(paciente);        
    }
    
    public PacienteDTOretorno retornarInfo(Long dui) {
        Paciente paciente= repositoryPaciente.findByDui(dui).orElseThrow(()-> new IllegalArgumentException("El paciente no existe"));
        PacienteDTOretorno retornarPaciente= new PacienteDTOretorno();
        retornarPaciente.setNombre(paciente.getNombre());
        retornarPaciente.setCorreo(paciente.getCorreo());
        retornarPaciente.setTelefono(paciente.getTelefono());
        retornarPaciente.setRol(paciente.getRol());
        
        return retornarPaciente;
    }
}
