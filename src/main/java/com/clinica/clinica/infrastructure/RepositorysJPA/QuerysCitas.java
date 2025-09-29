package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;

@Repository
public class QuerysCitas implements RepositoryCita {
    public MapperCitas mapper;
    public RepositoryCitas jpaCitas;
    public RepositoryMedico jpaDoctor;
    public QuerysPatient repositoryPacientes;
    RepositoryPaciente jpaPatient;

    @Autowired
    public QuerysCitas(RepositoryPaciente jpaPatient, MapperCitas mapper, RepositoryCitas jpaCitas, RepositoryMedico jpaDoctor) {
        this.mapper = mapper;
        this.jpaPatient = jpaPatient;
        this.jpaDoctor = jpaDoctor;
        this.jpaCitas = jpaCitas;
    }

    @Override
    public CitasDomain CrearCita(CitasDomain cita) {
        if (cita.getMedico().getNombre() == null || cita.getFechaCita() == null || cita.getMotivo().isBlank() || cita.getPaciente().getDui() == null) {
            throw new IllegalArgumentException("Rellena los campos.");
        }
        Medico entityDoctor = jpaDoctor.findByNombre(cita.getMedico().getNombre()).orElseThrow();
        Paciente patientEntity = jpaPatient.findByDui(cita.getPaciente().getDui()).orElseThrow(() -> new IllegalArgumentException("NO existe el paciente"));
        
        
        Citas añadirCita = new Citas();
        añadirCita.setPaciente(patientEntity);
        añadirCita.setMedico(entityDoctor);
        añadirCita.setEstado("Pendiente");
        añadirCita.setFechaCita(cita.getFechaCita());
        añadirCita.setMotivo(cita.getMotivo());
        añadirCita.setOrderId(cita.getOrderId());

        jpaCitas.save(añadirCita);

        return mapper.EntityToDomain(añadirCita);
    }

    @Override
    public Page<CitasDomain> EnlistarCitas(String correoAutenticado, String correoUrl, Pageable page) {
        boolean esMedico = jpaDoctor.findByCorreoInstitucional(correoAutenticado).isPresent();
        boolean esMismoPaciente = correoAutenticado.equals(correoUrl);

        if (!esMedico && !esMismoPaciente) {
            throw new AccessDeniedException("No tienes permitido realizar esta acción");
        }
        return jpaCitas.findByPaciente_Correo(correoUrl, page).map(mapper::EntityToDomain);
    }

    @Override
    public List<Object[]> CitasDelDia(String correo) {
        Medico medico = jpaDoctor.findByCorreoInstitucional(correo).orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));
        return jpaCitas.citasDelDia("Pendiente", medico);
    }

    @Override
    public CitasDomain CancelarCitas(Long cancelarCita) {
        Citas cancelar = jpaCitas.findById(cancelarCita).orElseThrow();
        cancelar.setEstado("Cancelado");
        jpaCitas.save(cancelar);
        return mapper.EntityToDomain(cancelar);
    }

    @Override
    public boolean AvaileableDateAndDoctor(MedicoDomain medico, LocalDateTime horaYfecha) {
        LocalTime hora = horaYfecha.toLocalTime();
        Medico medicoEntity = jpaDoctor.findById(medico.getId()).orElseThrow(() -> new IllegalArgumentException("El medico no existe"));

        if (hora.isBefore(medico.getHorarioInicio()) || hora.isAfter(medico.getHorarioCierre())) {
            throw new IllegalArgumentException("El horario debe ser conforme al del medico.");
        }

        boolean disponible = jpaCitas.existsByMedicoAndFechaCita(medicoEntity, horaYfecha);
        if (disponible) {
            throw new IllegalArgumentException("El horario está ocupado.");
        }
        return true;
    }

    @Override
    public Map<String, Long> ResumenDelMes() {
        Map<String, Long> resumen = new HashMap<>();
        resumen.put("Mes", jpaCitas.contarCitasDelMes());
        resumen.put("Completadas", jpaCitas.contarCitasCompletadasDelMes());
        resumen.put("Canceladas", jpaCitas.contarCitasCanceladasDelMes());
        return resumen;
    }

    @Override
    public List<Object[]> CitasPorEspecialidad() {
        List<Object[]>citasPorEspecialidad= jpaCitas.contarPorEspecialidad();
        if (citasPorEspecialidad.isEmpty()) {
            return citasPorEspecialidad;
        }
        return citasPorEspecialidad;
    }

    @Override
    public CitasDomain FindByOrderId(String orderId) {
         Citas cita= jpaCitas.findByOrderId(orderId).orElseThrow(()-> new IllegalArgumentException("No existe la orden."));
         
         cita.setEstado("Pagado");
         CitasDomain citaDomain= mapper.EntityToDomain(cita);
         
         jpaCitas.save(cita);
         return citaDomain;
    }
}
