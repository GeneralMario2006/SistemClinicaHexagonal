/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryMedicos;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Mappers.MapperMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mr587
 */
@Repository
public class QuerysMedico implements RepositoryMedicos {

    public MapperMedico mapper;
    public RepositoryMedico querysMedico;
    public PasswordEncoder PE;
    
    @Autowired
    public QuerysMedico(MapperMedico mapper, RepositoryMedico querysMedico, PasswordEncoder PE) {
        this.mapper = mapper;
        this.PE= PE;
        this.querysMedico = querysMedico;
    }
    
    @Override
    public MedicoDomain AgregarMedico(MedicoDomain medico) {
        if (querysMedico.findByCorreoInstitucional(medico.getCorreoInstitucional()).isPresent()) {
            throw new IllegalArgumentException("ya existe.");
        }
        medico.setContraseña(PE.encode(medico.getContraseña()));
        medico.setRol("MEDICO");
        
        Medico entidad= mapper.MedicoDomainToEntity(medico);
        Medico guardar= querysMedico.save(entidad);
        
        return mapper.MedicoEntityToDomain(guardar);
    }

    @Override
    public MedicoDomain FindById(Long id) {
        Medico entityDoctor= querysMedico.findById(id).orElseThrow(()-> new IllegalArgumentException("El medico no existe"));
        Medico save=entityDoctor;
        return mapper.MedicoEntityToDomain(save);
    }

    public Medico getEntityById(Long id) {
        return querysMedico.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe"));
    }
    
}
