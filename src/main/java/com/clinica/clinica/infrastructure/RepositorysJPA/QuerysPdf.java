/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.RepositorysDomain.PdfRepository;
import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mr587
 */
@Repository
public class QuerysPdf implements PdfRepository{
    
    RepositoryCitas jpaCitas;
    RepositoryMedico jpaDoctor;

    @Autowired
    public QuerysPdf(RepositoryCitas jpaCitas, RepositoryMedico jpaDoctor) {
        this.jpaCitas = jpaCitas;
        this.jpaDoctor= jpaDoctor;
    }
    
    
    @Override
    public String GenerarPdf(UpdateCitaDto dto, String principal) {
        try {
            Citas citaId = jpaCitas.findById(dto.getIdCita()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada."));

            if (!citaId.getEstado().equalsIgnoreCase("Pendiente")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No puedes aceptar citas completadas o canceladas.");
            }
            Medico medicoId = jpaDoctor.findByCorreoInstitucional(principal).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Médico no válido."));

            if (!citaId.getMedico().getId().equals(medicoId.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No eres el médico asignado a esta cita.");
            }

            String folderPath = "historiales";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            String nombrePaciente = citaId.getPaciente().getNombre().replaceAll("[^a-zA-Z0-9]", "_");
            String fileName = "Historial_" + nombrePaciente + "_" + citaId.getId() + ".pdf";
            String ruta = folderPath + File.separator + fileName;

            Document document = new Document();
            OutputStream outOut = new FileOutputStream(ruta);
            PdfWriter.getInstance(document, outOut);
            document.open();

            document.add(new Paragraph("REGISTRO MÉDICO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Paciente: " + citaId.getPaciente().getNombre()));
            document.add(new Paragraph("Médico: " + citaId.getMedico().getNombre()));
            document.add(new Paragraph("Especialidad: " + citaId.getMedico().getEspecialidad()));
            document.add(new Paragraph("Diagnóstico: " + dto.getDiagnostico()));
            document.add(new Paragraph("Observaciones: " + (dto.getObservacion() == null || dto.getObservacion().isBlank() ? "Ninguna." : dto.getObservacion())));
            document.add(new Paragraph("Fecha cita: " + citaId.getFechaCita()));
            document.add(new Paragraph("-------------------"));

            
            document.close();
            outOut.close();
            citaId.setEstado("Completada");
            jpaCitas.save(citaId);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear el archivo PDF.");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al cerrar archivo PDF.");
        } catch (DocumentException ex) {
            Logger.getLogger(QuerysCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "PDF guardado en el servidor.";
    }
}
