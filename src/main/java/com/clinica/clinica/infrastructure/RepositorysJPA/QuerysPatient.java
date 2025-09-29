/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryPacientes;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.Mappers.MapperPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mr587
 */
@Repository
public class QuerysPatient implements RepositoryPacientes{
public MapperPaciente patientMapper;
public RepositoryPaciente patientJpa;
public PasswordEncoder PE;

@Autowired
public QuerysPatient(MapperPaciente patientMapper, RepositoryPaciente patientJpa, PasswordEncoder PE) {
        this.patientMapper = patientMapper;
        this.PE= PE;
        this.patientJpa = patientJpa;
    }
        
    @Override
    public PacientesDomain AgregarPaciente(PacientesDomain paciente) {
        if (patientJpa.findByCorreo(paciente.getCorreo()).isPresent() || patientJpa.findByDui(paciente.getDui()).isPresent()) {
            throw new IllegalArgumentException("El paciente ya existe. ");
        }
        if (paciente.getCorreo()==null || paciente.getDui()==null || paciente.getNombre()==null || paciente.getTelefono()==null) {
           throw new IllegalArgumentException("Faltan campos. ");
       }
        paciente.setContraseña(PE.encode(paciente.getContraseña()));
        paciente.setRol("PACIENTE");
            Paciente entidad = patientMapper.DomainToEntity(paciente);
                Paciente guardado = patientJpa.save(entidad);
        return patientMapper.EntityToDomain(guardado);
    }

    

    @Override
    public PacientesDomain BuscarPacientePorCorreo(String correo) {
        throw new UnsupportedOperationException("No implementado aún.");
   }

    @Override
public PacientesDomain FindByDui(Long dui) {
    Paciente entity = patientJpa.findByDui(dui)
        .orElseThrow(() -> new IllegalArgumentException("El paciente no existe."));
    return patientMapper.EntityToDomain(entity);
}

    
}
