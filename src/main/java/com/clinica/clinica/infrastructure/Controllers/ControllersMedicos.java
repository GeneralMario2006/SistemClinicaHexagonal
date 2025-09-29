package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.clinica.Application.Service.DoctorService;
import com.clinica.clinica.Application.Service.PDFservice;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.infrastructure.RequestDTO.ActualizarCitasDTO;
import com.clinica.clinica.infrastructure.RequestDTO.MedicoDTO;
import com.clinica.clinica.infrastructure.JWT.JwtProvider;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import com.clinica.clinica.infrastructure.Mappers.MapperMedico;
import com.clinica.clinica.infrastructure.RequestDTO.RegisterDoctorDto;
import com.clinica.clinica.infrastructure.Security.MedicoDetails;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/SesionMedicos")
public class ControllersMedicos {

    @Autowired
    MapperMedico mapperMedico;
    
    @Autowired
    JwtProvider jwt;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    MapperCitas mapperDates;
    
    @Autowired
    DoctorService serviceMedico;
    
    @Autowired
    PDFservice pdfService;
    
    @PostMapping("/Registro")
    public ResponseEntity<?> Registrar(@RequestBody RegisterDoctorDto medicoDto) {
        try {
            MedicoDomain domain = mapperMedico.DoctorDtToDomain(medicoDto);
            serviceMedico.SaveDoctor(domain);
            return ResponseEntity.ok("Registrado con exito");
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MedicoDTO medico) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            medico.getCorreoInstitucional(), medico.getContraseña()
                    )
            );
            MedicoDetails medicoDetails = (MedicoDetails) authentication.getPrincipal();
            
            boolean tieneRolPaciente = medicoDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_MEDICO"));
            
            if (!tieneRolPaciente) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene el rol requerido.");
            }
            
            String tokenUsername = jwt.generateToken(medicoDetails);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("token", tokenUsername));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas." + e.getMessage());
        }
    }
    
    @PostMapping("/ActualizarCita")
    public ResponseEntity<?> Actualizar(@RequestBody ActualizarCitasDTO id, Principal principal) {
        try {
            UpdateCitaDto updateDateDomain = mapperDates.UpdateDtoToDomain(id);
            String correoPrincipal = principal.getName();
            pdfService.AceptarCita(updateDateDomain, correoPrincipal);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.badRequest().body("Error: " + e);
        }
    }
    
    @GetMapping("/GetAllDoctors")
    public ResponseEntity<?> GetDoctors() {
        return ResponseEntity.ok(serviceMedico.GetAllDoctors());
    }
    
}
