package com.clinica.proyect.ValidacionesPacientes;

import com.clinica.proyect.DTOS.CitaDTO;
import com.clinica.proyect.Entitys.Citas;
import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Entitys.Paciente;
import com.clinica.proyect.Repositorys.RepositoryCitas;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import com.clinica.proyect.Repositorys.RepositoryPaciente;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitasValidaciones {
    @Autowired
    RepositoryPaciente repositoryPaciente;
    
    @Autowired
    RepositoryCitas repositoryCitas;
    
    @Autowired
    RepositoryMedico repoMedico;
    
    public boolean existPaciente(Long dui) {
        return repositoryPaciente.findByDui(dui).isPresent();
    }
    
public void crearCita(CitaDTO citas) {   
        
        if (citas.getIdMedico()==null || citas.getFechaCita()==null || citas.getMotivo()==null) {
throw new IllegalArgumentException("Rellena los campos.");
        }
        LocalDateTime fecha= citas.getFechaCita();
        Medico medico = repoMedico.findById(citas.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        
        LocalTime hora= fecha.toLocalTime();
        if (hora.isBefore(medico.getHorarioInicio()) || hora.isAfter(medico.getHorarioCierre())) {
            throw new IllegalArgumentException("El horario debe ser conforme al del medico.");
        }
        
        boolean disponible= repositoryCitas.existsByMedicoAndFechaCita(medico, fecha);
        if ( disponible) {
            throw new IllegalArgumentException("El horario está ocupado.");
        }
        
        Paciente findPaciente= repositoryPaciente.findByDui(citas.getDui()).orElseThrow();
        Citas añadirCita= new Citas();
        añadirCita.setPaciente(findPaciente);
        añadirCita.setMedico(medico);
        añadirCita.setEstado("Pendiente");
        añadirCita.setFechaCita(citas.getFechaCita());
        añadirCita.setMotivo(citas.getMotivo());
        repositoryCitas.save(añadirCita);
        
    }

}
