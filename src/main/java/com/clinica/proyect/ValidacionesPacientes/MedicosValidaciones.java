/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.proyect.ValidacionesPacientes;

import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author mr587
 */
@Component
public class MedicosValidaciones {
    @Autowired
    RepositoryMedico medicoRepository;
    
    @Autowired
   PasswordEncoder PE;
    
    public boolean validarMedico(String correo) {
        return medicoRepository.findByCorreoInstitucional(correo).isPresent();
    }
    
    public void añadirMedico(Medico medico) {
        medicoRepository.save(medico);
        String correo= medico.getNombre()+medico.getId()+"@gob.sv";
        
        medico.setCorreoInstitucional(correo);
        medico.setContraseña(PE.encode(medico.getContraseña()));
        medico.setRol("MEDICO");
        medico.setHorarioInicio(LocalTime.of(8, 0));
        medico.setHorarioCierre(LocalTime.of(17, 0));
        medicoRepository.save(medico);
    }
}
