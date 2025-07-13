/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Mappers;

import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.RequestDTO.RegisterDoctorDto;
import org.mapstruct.Mapper;

/**
 *
 * @author mr587
 */
@Mapper(componentModel = "spring")
public interface MapperMedico {
    public default Medico MedicoDomainToEntity (MedicoDomain medicoDomain) {
        return new Medico(
                medicoDomain.getCorreoInstitucional(),
                medicoDomain.getNombre(),
                medicoDomain.getEspecialidad(),
                medicoDomain.getContraseña(),
                medicoDomain.getHorarioInicio(),
                medicoDomain.getHorarioCierre(),
                medicoDomain.getFirma()
        );
    }
    
    public default MedicoDomain MedicoEntityToDomain(Medico medicoEntity) {
       return new MedicoDomain(medicoEntity.getId(),
        medicoEntity.getCorreoInstitucional(),
               medicoEntity.getNombre(),
               medicoEntity.getEspecialidad(),
               medicoEntity.getContraseña(),
               medicoEntity.getHorarioInicio(),
               medicoEntity.getHorarioCierre(),
               medicoEntity.getFirma()
       );
    }
    
    public default MedicoDomain DoctorDtToDomain(RegisterDoctorDto dtoDoctor) {
        return new MedicoDomain(
        null, dtoDoctor.getCorreoInstitucional(), dtoDoctor.getNombre(), dtoDoctor.getEspecialidad(),
                dtoDoctor.getContraseña(), dtoDoctor.getHorarioInicio(), dtoDoctor.getHorarioCierre(),
                null
        );
    }
}
