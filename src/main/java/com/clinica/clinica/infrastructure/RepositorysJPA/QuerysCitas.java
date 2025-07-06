package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import com.clinica.clinica.infrastructure.Mappers.MapperMedico;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;

@Repository
public class QuerysCitas implements RepositoryCita {
public MapperCitas mapper;
public RepositoryCitas jpaCitas;

public RepositoryMedico jpaDoctor;
public QuerysPatient repositoryPacientes;
        MapperMedico mapperDoctor;
        RepositoryPaciente jpaPatient;
@Autowired
    public QuerysCitas(RepositoryPaciente jpaPatient, MapperCitas mapper, RepositoryCitas jpaCitas, RepositoryMedico jpaDoctor) {
        this.mapper = mapper;
        this.jpaPatient= jpaPatient;
        this.jpaDoctor= jpaDoctor;
        this.jpaCitas = jpaCitas;
    }


    
    @Override
    public CitasDomain CrearCita(CitasDomain cita) {
        if (cita.getMedico().getId()==null || cita.getFechaCita()==null || cita.getMotivo().isBlank() || cita.getPaciente().getDui()==null) {
            throw new IllegalArgumentException("Rellena los campos.");
        }
        Medico entityDoctor= jpaDoctor.findById(cita.getMedico().getId()).orElseThrow();
        Paciente patientEntity= jpaPatient.findByDui(cita.getPaciente().getDui()).orElseThrow(()-> new IllegalArgumentException("NO existe el paciente"));
        
        Citas añadirCita= new Citas();
        añadirCita.setPaciente(patientEntity);
        añadirCita.setMedico(entityDoctor);
        añadirCita.setEstado("Pendiente");
        añadirCita.setFechaCita(cita.getFechaCita());
        añadirCita.setMotivo(cita.getMotivo());
        jpaCitas.save(añadirCita);
        
        return mapper.EntityToDomain(añadirCita);
    }

   @Override
public List<CitasDomain> EnlistarCitas(String correoAutenticado, String correoUrl) {
    try {
        boolean esMedico = jpaDoctor.findByCorreoInstitucional(correoAutenticado).isPresent();
        boolean esMismoPaciente = correoAutenticado.equals(correoUrl);

        if (!esMedico && !esMismoPaciente) {
            throw new AccessDeniedException("No tienes permitido realizar esta acción");
        }
        return jpaCitas.findByPaciente_Correo(correoUrl).stream()
                .map(mapper::EntityToDomain)
                .collect(Collectors.toList());

    } catch (Exception e) {
        System.out.println("error: " + e.getMessage());
        return Collections.emptyList(); // Mejor que null
    }
}


    @Override
    public Object[] CitasDelDia(String correo) {
Medico medico= jpaDoctor.findByCorreoInstitucional(correo).get();
Object[] citas = jpaCitas.citasDelDia("Pendiente", medico);
if (citas.length==0) {
    throw new IllegalArgumentException("No hay citas este dia");
}
return jpaCitas.citasDelDia("Pendiente",medico);
    }

    @Override
    public CitasDomain CancelarCitas(Long cancelarCita) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean AvaileableDateAndDoctor(MedicoDomain medico, LocalDateTime horaYfecha) {
        
        LocalDateTime fecha= horaYfecha;
        Medico medicoEntity = jpaDoctor.findById(medico.getId()).orElseThrow(()->new IllegalArgumentException("El medico no existe"));
        
        
        LocalTime hora= fecha.toLocalTime();
        if (hora.isBefore(medico.getHorarioInicio()) || hora.isAfter(medico.getHorarioCierre())) {
            throw new IllegalArgumentException("El horario debe ser conforme al del medico.");
        }
        
        boolean disponible= jpaCitas.existsByMedicoAndFechaCita(medicoEntity, fecha);
        if (disponible) {
            throw new IllegalArgumentException("El horario está ocupado.");
        }
        return disponible;
        
    }
    
}
