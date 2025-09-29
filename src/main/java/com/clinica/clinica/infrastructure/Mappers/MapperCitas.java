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
                new MedicoDomain(citaEntity.getMedico().getId(), citaEntity.getMedico().getCorreoInstitucional(), citaEntity.getMedico().getNombre(),
                        citaEntity.getMedico().getEspecialidad(), citaEntity.getMedico().getContraseña(), citaEntity.getMedico().getHorarioInicio(),
                        citaEntity.getMedico().getHorarioCierre(), citaEntity.getMedico().getRol()),
                citaEntity.getFechaCita(),
                citaEntity.getEstado(),
                citaEntity.getMotivo(),
                citaEntity.getOrderId()
        );
    }

    public default Citas CitasDomainToEntity(CitasDomain citaDomain) {
        return new Citas(
                new Paciente(citaDomain.getPaciente().getNombre(), citaDomain.getPaciente().getCorreo(),
                        citaDomain.getPaciente().getDui(),
                        citaDomain.getPaciente().getFechaNacimiento(), citaDomain.getPaciente().getTelefono(),
                        citaDomain.getPaciente().getContraseña(), citaDomain.getPaciente().getRol()),
                 new Medico(citaDomain.getMedico().getCorreoInstitucional(), citaDomain.getMedico().getNombre(),
                        citaDomain.getMedico().getEspecialidad(), citaDomain.getMedico().getContraseña(),
                        citaDomain.getMedico().getHorarioInicio(), citaDomain.getMedico().getHorarioCierre(), citaDomain.getMedico().getRol()
                ),
                 citaDomain.getFechaCita(),
                citaDomain.getEstado(),
                citaDomain.getMotivo(),
                citaDomain.getOrderId()
        );

    }

    public default CitasDomain CitasDtoToDomain(CitaDTO citaDto) {
        return new CitasDomain(
                new PacientesDomain(null, null, null, citaDto.getDui(), null, null, null, null),
                new MedicoDomain(null, null, citaDto.getNombreMedico(), null, null, null, null, null),
                citaDto.getFechaCita(),
                null,
                citaDto.getMotivo(),
                null
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
