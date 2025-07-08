package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public void GenerarPdf(UpdateCitaDto dto, String principal) {
        try {
            
            Citas citaId = jpaCitas.findById(dto.getIdCita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada."));
            
            if (!citaId.getEstado().equalsIgnoreCase("Pendiente")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No puedes aceptar citas completadas o canceladas.");
            }
            Medico medicoId = jpaDoctor.findByCorreoInstitucional(principal)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Médico no válido."));
            
            if (!citaId.getMedico().getId().equals(medicoId.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No eres el médico asignado a esta cita.");
            }

            // Crear la carpeta si no existe
            String folderPath = "historiales";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String nombrePaciente = citaId.getPaciente().getNombre().replaceAll("[^a-zA-Z0-9]", "_");
            String fileName = "Historial_" + nombrePaciente + "_" + citaId.getId() + ".pdf";
            String ruta = folderPath + File.separator + fileName;
            
            Document document = new Document();
            OutputStream outOut = new FileOutputStream(ruta);
            PdfWriter.getInstance(document, outOut);
            document.open();

            // Escribir contenido
            document.add(new Paragraph("REGISTRO MÉDICO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Paciente: " + citaId.getPaciente().getNombre()));
            document.add(new Paragraph("Médico: " + citaId.getMedico().getNombre()));
            document.add(new Paragraph("Especialidad: " + citaId.getMedico().getEspecialidad()));
            document.add(new Paragraph("Diagnóstico: " + dto.getDiagnostico()));
            document.add(new Paragraph("Observaciones: " +
                    (dto.getObservacion() == null || dto.getObservacion().isBlank() ? "Ninguna." : dto.getObservacion())));
            document.add(new Paragraph("Fecha cita: " + citaId.getFechaCita()));

            // Cerrar documento
            document.close();
            outOut.close();

            // Marcar cita como completada
            citaId.setEstado("Completada");
            jpaCitas.save(citaId);

            System.out.println("PDF generado correctamente en: " + ruta);

        } catch (FileNotFoundException e) {
            System.out.println("Error: "+e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear el archivo PDF.");
        } catch (IOException e) {
            System.out.println("Error al cerrar: "+e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al cerrar archivo PDF.");
        }
    }
    
    }
