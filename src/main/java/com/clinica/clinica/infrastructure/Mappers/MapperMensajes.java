/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Mappers;

import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.MensajesDomain;
import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.infrastructure.Entitys.Mensajes;
import com.clinica.clinica.infrastructure.RequestDTO.MensajeDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author mr587
 */
@Mapper(componentModel = "spring")
public interface MapperMensajes {
    
    public default MensajesDomain MsjDtoToDomain(MensajeDTO dto) {
        return new MensajesDomain(
        new PacientesDomain(null, null, dto.getCorreo(), null,null,null,null,null)
                ,
                new MedicoDomain(null, dto.getCorreo(), null,null,null,null,null, null)
                ,
                dto.getContenido(),
                null
        );
    }
    
    public default MensajesDomain MsjEntityToDomain(Mensajes entityMsj) {
        return new MensajesDomain(
        new PacientesDomain (entityMsj.getPaciente().getId(), entityMsj.getPaciente().getNombre(), entityMsj.getPaciente().getCorreo(), entityMsj.getPaciente().getDui(), entityMsj.getPaciente().getFechaNacimiento(), entityMsj.getPaciente().getTelefono(), entityMsj.getPaciente().getContraseña(), entityMsj.getPaciente().getRol())
                ,
                new MedicoDomain(
                        entityMsj.getMedico().getId(), entityMsj.getMedico().getCorreoInstitucional(), entityMsj.getMedico().getNombre(), entityMsj.getMedico().getEspecialidad(), entityMsj.getMedico().getContraseña(), entityMsj.getMedico().getHorarioInicio(), entityMsj.getMedico().getHorarioCierre(), entityMsj.getMedico().getRol())
                ,
                entityMsj.getContenido(),
                entityMsj.getRemitente()
        );
    }
}
