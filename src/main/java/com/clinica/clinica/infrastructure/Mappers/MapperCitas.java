/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Mappers;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.RequestDTO.ActualizarCitasDTO;
import com.clinica.clinica.infrastructure.RequestDTO.CitaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperCitas {
    public default CitasDomain EntityToDomain(Citas citaEntity) {
        return new CitasDomain(
                new PacientesDomain(citaEntity.getPaciente().getId(), citaEntity.getPaciente().getNombre(), citaEntity.getPaciente().getCorreo(), 
                        citaEntity.getPaciente().getDui(), citaEntity.getPaciente().getFechaNacimiento(), citaEntity.getPaciente().getTelefono(), 
                        citaEntity.getPaciente().getContraseña(), citaEntity.getPaciente().getRol()),
                
                new MedicoDomain(citaEntity.getMedico().getId(),  citaEntity.getMedico().getCorreoInstitucional(),  citaEntity.getMedico().getNombre(), 
                citaEntity.getMedico().getEspecialidad(),  citaEntity.getMedico().getContraseña(),  citaEntity.getMedico().getHorarioInicio(), 
                citaEntity.getMedico().getHorarioCierre()), 
                
                    citaEntity.getFechaCita(),
                    citaEntity.getEstado(), 
                    citaEntity.getMotivo()
        );
    }
    
    public default Citas CitasDomainToEntity(CitasDomain citaDomain) {
        return new Citas(
        new Paciente(citaDomain.getPaciente().getNombre(), citaDomain.getPaciente().getCorreo(), 
        citaDomain.getPaciente().getDui(),
        citaDomain.getPaciente().getFechaNacimiento(), citaDomain.getPaciente().getTelefono(),
        citaDomain.getPaciente().getContraseña(), citaDomain.getPaciente().getRol())
                ,
                new Medico(citaDomain.getMedico().getCorreoInstitucional(), citaDomain.getMedico().getNombre(),
                citaDomain.getMedico().getEspecialidad(), citaDomain.getMedico().getContraseña(), 
                citaDomain.getMedico().getHorarioInicio(), citaDomain.getMedico().getHorarioCierre()
                )
                ,
                        citaDomain.getFechaCita(),
                        citaDomain.getEstado(),
                        citaDomain.getMotivo()
        );
        
    }
            public default CitasDomain CitasDtoToDomain(CitaDTO citaDto) {
            return new CitasDomain(
            new PacientesDomain(null, null, null, citaDto.getDui(), null, null, null, null),
                   new MedicoDomain(citaDto.getIdMedico(), null, null, null, null, null, null),
                citaDto.getFechaCita(),
                    null,
                    citaDto.getMotivo()
            ); 
        }
        
            public default UpdateCitaDto UpdateDtoToDomain(ActualizarCitasDTO dto) {
                return new UpdateCitaDto(
                dto.getIdCita(), 
                        dto.getDiagnostico(),
                        dto.getMedicamentos(),
                        dto.getObservacion()
                );
            }
}
