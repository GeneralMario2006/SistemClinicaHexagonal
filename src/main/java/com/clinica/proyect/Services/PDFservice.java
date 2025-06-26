package com.clinica.proyect.Services;

import com.clinica.proyect.DTOS.ActualizarCitasDTO;
import com.clinica.proyect.Entitys.Citas;
import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Repositorys.RepositoryCitas;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PDFservice {

    @Autowired
    RepositoryCitas citasRepository;

    @Autowired
    RepositoryMedico medicoRepository;

    public void GenerarPDF(ActualizarCitasDTO dto, Principal principal) {
        try {
            Citas citaId = citasRepository.findById(dto.getIdCita()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada."));
            Medico medicoId = medicoRepository.findByCorreoInstitucional(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Médico no válido."));

            if (dto.getDiagnostico().isBlank() || dto.getMedicamentos().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hay campos inválidos.");
            }
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
            citasRepository.save(citaId);

            System.out.println("PDF generado correctamente en: " + ruta);

        } catch (FileNotFoundException e) {
            Logger.getLogger(PDFservice.class.getName()).log(Level.SEVERE, "Archivo no encontrado", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear el archivo PDF.");
        } catch (IOException e) {
            Logger.getLogger(PDFservice.class.getName()).log(Level.SEVERE, "Error al cerrar archivo", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al cerrar archivo PDF.");
        }
    }
}

    