package com.clinica.proyect.Services;

import com.clinica.proyect.DTOS.CitaDTO;
import com.clinica.proyect.Entitys.Citas;
import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Repositorys.RepositoryCitas;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import com.clinica.proyect.ValidacionesPacientes.CitasValidaciones;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceCita {

    @Autowired
    CitasValidaciones validarCitas;

    @Autowired
    RepositoryCitas repositoryCitas;

    @Autowired
    RepositoryMedico medicoRepository;

    public void addCita(CitaDTO cita) {
        if (validarCitas.existPaciente(cita.getDui())) {
            validarCitas.crearCita(cita);
        } else {
            System.out.println("El paciente no existe.");
        }
    }
    
    public void CancelarCita(Long id) {
        Citas cancelar= repositoryCitas.findById(id).get();
        cancelar.setEstado("Cancelado");
        repositoryCitas.save(cancelar);
    }

    public Page<Citas> listarCitas(Principal principal, String correoUrl, int page, int size) {
        System.out.println("dentro del metodo");
        String correoAutenticado = principal.getName();

        try {
            boolean esMedico = medicoRepository.findByCorreoInstitucional(correoAutenticado).isPresent();
            boolean esMismoPaciente = correoAutenticado.equals(correoUrl);

            // Si no es médico ni es el paciente dueño del correo denegar
            if (!esMedico && !esMismoPaciente) {
                throw new AccessDeniedException("No tienes permitido realizar esta acción");
            }

            Pageable pageable = PageRequest.of(page, size);
            return repositoryCitas.findByPaciente_CorreoOrderByFechaCitaAsc(correoUrl, pageable);

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }
    
    public Page<Object[]>CitasDelDia(Principal principal, int page, int size){
        Medico medico= medicoRepository.findByCorreoInstitucional(principal.getName()).get();
        

        Pageable pageable = PageRequest.of(page, size);
Page<Object[]> citas = repositoryCitas.citasDelDia("Pendiente", medico, pageable);
System.out.println("Cantidad de citas encontradas: " + citas.getTotalElements());

        return repositoryCitas.citasDelDia("Pendiente",medico, pageable);
    }
}
