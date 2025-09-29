/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.MensajesDomain;
import com.clinica.clinica.domain.RepositorysDomain.MensajesRepositoryNoJpa;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author mr587
 */
@Service
public class ServiceMessage {
    MensajesRepositoryNoJpa repositoryMessage;

    public ServiceMessage(MensajesRepositoryNoJpa repositoryMessage) {
        this.repositoryMessage = repositoryMessage;
    }
    //Patients
    public void EnviarMsjComoPaciente(String correo, MensajesDomain domain) {
        System.out.println("Correo principal: "+correo);
        System.out.println("Domain= "+ domain.getMedico().getCorreoInstitucional()+ "mensaje; "+ domain.getContenido());
        repositoryMessage.EnviaMensajeComoPaciente(correo, domain);
    }
    public List<Object>VerMensajesComoPaciente(String paciente, String medico) {
        return repositoryMessage.VerMensajesComoPacienteDeMedicos(paciente, medico);
    }
    
    //Doctors
    public List<Object>VerMensajesComoMedicoPacientes(String principal, String patient) {
        return repositoryMessage.VerMensajesComoMedicoDePacientes(principal, patient);
    }
    
    public void enviarMessageLikeDoctor(String emailPrincipal, MensajesDomain domainMsj) {
        repositoryMessage.EnviaMensajeComoMedico(emailPrincipal, domainMsj);
    }
}
