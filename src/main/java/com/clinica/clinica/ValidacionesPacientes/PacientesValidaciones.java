/*

package com.clinica.proyect.ValidacionesPacientes;

import com.clinica.clinica.infrastructure.RequestDTO.PacienteDTOretorno;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/*
@Component
public class PacientesValidaciones {
    @Autowired
   
    
    @Autowired
    PasswordEncoder PE;
    
    
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
*/