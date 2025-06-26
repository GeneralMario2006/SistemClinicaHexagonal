/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.proyect.Services;

import com.clinica.proyect.DTOS.MensajeDTO;
import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Entitys.Mensajes;
import com.clinica.proyect.Entitys.Paciente;
import com.clinica.proyect.Repositorys.MensajesRepository;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import com.clinica.proyect.Repositorys.RepositoryPaciente;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mr587
 */
@Service
public class ServicesMensajes {
    @Autowired
    MensajesRepository msjRepository;
    
    @Autowired
    RepositoryPaciente pacienteRepository;
    
    @Autowired
    RepositoryMedico medicoRepository;
    
    public void EnviaMensajePaciente(Principal principal, MensajeDTO dto) {
        if (dto.getContenido().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar texto");
        }
        Paciente paciente= pacienteRepository.findByCorreo(principal.getName()).get();
        Medico medico= medicoRepository.findByCorreoInstitucional(dto.getCorreo()).orElseThrow();
        
        Mensajes msj= new Mensajes();
        msj.setPaciente(paciente);
        msj.setMedico(medico);
        msj.setContenido(dto.getContenido());
        msj.setRemitente("PACIENTE");
        msjRepository.save(msj);
    }
    
    public List<Object>VerMensajesMedicos(Principal principal, String correoMedico) {
        Paciente paciente= pacienteRepository.findByCorreo(principal.getName()).get();
        Medico medico= medicoRepository.findByCorreoInstitucional(correoMedico).orElseThrow();
        return msjRepository.findMensajesEntrePacienteYMedico(paciente.getCorreo(), medico.getCorreoInstitucional());   
    }
    
    public List<Object>VerMensajesPacientes(Principal principal, String correoMedico) {
        Paciente paciente= pacienteRepository.findByCorreo(correoMedico).orElseThrow();
        Medico medico= medicoRepository.findByCorreoInstitucional(principal.getName()).get();
        return msjRepository.findMensajesEntrePacienteYMedico(paciente.getCorreo(), medico.getCorreoInstitucional());   
    }
    
    public void EnviaMensajeMedico(Principal principal, MensajeDTO dto) {
        if (dto.getContenido().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar texto");
        }
        Paciente paciente= pacienteRepository.findByCorreo(dto.getCorreo()).get();
        Medico medico= medicoRepository.findByCorreoInstitucional(principal.getName()).orElseThrow();
        
        Mensajes msj= new Mensajes();
        msj.setPaciente(paciente);
        msj.setMedico(medico);
        msj.setContenido(dto.getContenido());
        msj.setRemitente("MEDICO");
        msjRepository.save(msj);
    }
}
