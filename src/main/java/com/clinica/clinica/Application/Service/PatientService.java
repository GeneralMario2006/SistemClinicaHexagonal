/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryPacientes;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    RepositoryPacientes patientRepository;

    public PatientService(RepositoryPacientes patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    
    public String SavePatient(PacientesDomain pacienteDomain) {
        try {
        patientRepository.AgregarPaciente(pacienteDomain);
        return "Guardado";
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: "+e);
        }        
    }
}
