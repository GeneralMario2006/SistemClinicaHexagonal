/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.RepositorysDomain.PdfRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mr587
 */
@Service
public class PDFservice {
 
    PdfRepository repositoryPdf;

    public PDFservice(PdfRepository repositoryPdf) {
        this.repositoryPdf = repositoryPdf;
    }
    
    public String AceptarCita(UpdateCitaDto domainDto, String emailPrincipal) {
        if (domainDto.getDiagnostico().isBlank() || domainDto.getMedicamentos().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hay campos inválidos.");
            }
        return repositoryPdf.GenerarPdf(domainDto, emailPrincipal);
    }
}
