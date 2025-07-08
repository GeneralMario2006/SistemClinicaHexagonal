/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.MensajesDomain;
import com.clinica.clinica.domain.RepositorysDomain.MensajesRepositoryNoJpa;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Mensajes;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mr587
 */
@Repository
public class QuerysMensajes implements MensajesRepositoryNoJpa{
    
    private RepositoryMedico medicoJpa;
    private RepositoryPaciente patientJpa;
    private RepositoryJpaMensajes msjJpa;
    
    @Autowired 
    public QuerysMensajes(RepositoryMedico medicoJpa, RepositoryPaciente patientJpa, RepositoryJpaMensajes msjJpa) {
        this.medicoJpa = medicoJpa;
        this.patientJpa = patientJpa;
        this.msjJpa = msjJpa;
    }


    @Override
    public void EnviaMensajeComoPaciente(String CorreoPrincipal, MensajesDomain MsjDomain) {
 if (MsjDomain.getContenido().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar texto");
        }
        Paciente paciente= patientJpa.findByCorreo(CorreoPrincipal).get();
        Medico medico= medicoJpa.findByCorreoInstitucional(MsjDomain.getMedico().getCorreoInstitucional()).orElseThrow();
        
        Mensajes msj= new Mensajes();
        msj.setPaciente(paciente);
        msj.setMedico(medico);
        msj.setContenido(MsjDomain.getContenido());
        msj.setRemitente("PACIENTE");
        msjJpa.save(msj);
    }
    @Override
    public List<Object> VerMensajesComoPacienteDeMedicos(String correoPrincipal, String correoMedico) {
Paciente paciente= patientJpa.findByCorreo(correoPrincipal).get();
        Medico medico= medicoJpa.findByCorreoInstitucional(correoMedico).orElseThrow();
        return msjJpa.findMensajesEntrePacienteYMedico(paciente.getCorreo(), medico.getCorreoInstitucional());
    }
    

    @Override
    public List<Object> VerMensajesComoMedicoDePacientes(String principal, String correo) {
        Paciente paciente= patientJpa.findByCorreo(correo).orElseThrow();
        Medico medico= medicoJpa.findByCorreoInstitucional(principal)
                .orElseThrow(() ->new IllegalArgumentException("The doctor dosen´t exist."));
        return msjJpa.findMensajesEntrePacienteYMedico(paciente.getCorreo(), medico.getCorreoInstitucional());
    }

    @Override
    public void EnviaMensajeComoMedico(String principal, MensajesDomain dto) {
if (dto.getContenido().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar texto");
        }
        Paciente paciente= patientJpa.findByCorreo(dto.getPaciente().getCorreo())
                .orElseThrow(()-> new IllegalArgumentException("The destinator dosen´t exist."));
        Medico medico= medicoJpa.findByCorreoInstitucional(principal).get();
        
        Mensajes msj= new Mensajes();
        msj.setPaciente(paciente);
        msj.setMedico(medico);
        msj.setContenido(dto.getContenido());
        msj.setRemitente("MEDICO");
        msjJpa.save(msj);
    }
    
}
