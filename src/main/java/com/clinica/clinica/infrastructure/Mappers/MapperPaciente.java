/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Mappers;

import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.RequestDTO.RequestPacientes;
import org.mapstruct.Mapper;

/**
 *
 * @author mr587
 */
@Mapper(componentModel = "spring")
public interface MapperPaciente {
    public default Paciente DomainToEntity(PacientesDomain pacienteDomain) {
        return new Paciente(
        pacienteDomain.getNombre(),
                pacienteDomain.getCorreo(),
                pacienteDomain.getDui(),
                pacienteDomain.getFechaNacimiento(),
                pacienteDomain.getTelefono(),
                pacienteDomain.getContraseña(),
                pacienteDomain.getRol()
        );
    }
    
    public default PacientesDomain EntityToDomain(Paciente paciente) {
        return new PacientesDomain( 
        paciente.getId(),
                paciente.getNombre(),
                paciente.getCorreo(),
                paciente.getDui(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getContraseña(), 
                paciente.getRol()
        );
    }
    
    public default PacientesDomain RequestToDomain(RequestPacientes Pacientedto) {
        return new PacientesDomain(
        null,
                Pacientedto.getNombre(),
                Pacientedto.getCorreo(),
                Pacientedto.getDui(),
                Pacientedto.getFechaNacimiento(),
                Pacientedto.getTelefono(),
                Pacientedto.getContraseña(),
                null
        );
    }
}
