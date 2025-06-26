/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.proyect.Services;

import com.clinica.proyect.DTOS.PacienteDTOretorno;
import com.clinica.proyect.Entitys.Paciente;
import com.clinica.proyect.Repositorys.RepositoryPaciente;
import com.clinica.proyect.ValidacionesPacientes.PacientesValidaciones;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePaciente {
    @Autowired
    PacientesValidaciones validar;
    
   
    
    public void addPaciente(Paciente p) {
        try{
        if (!validar.ExistYCrearPaciente(p)) {
            validar.crearPaciente(p);
        }
        throw new IllegalArgumentException("Error el paciente ya existe.");
        }catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public PacienteDTOretorno BuscarPaciente(Long dui) {
        return validar.retornarInfo(dui);   
    }
    
    /*public boolean esPacienteOno(Principal principal) {
        return repositoryPaciente.findByCorreo(principal.getName()).isPresent();
    }
    */
}
