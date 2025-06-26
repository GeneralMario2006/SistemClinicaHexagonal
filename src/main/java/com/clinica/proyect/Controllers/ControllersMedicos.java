package com.clinica.proyect.Controllers;

import com.clinica.proyect.DTOS.ActualizarCitasDTO;
import com.clinica.proyect.DTOS.MedicoDTO;
import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.JWT.JwtProvider;
import com.clinica.proyect.Security.MedicoDetails;
import com.clinica.proyect.Services.PDFservice;
import com.clinica.proyect.Services.ServiceMedico;
import com.clinica.proyect.ValidacionesPacientes.MedicosValidaciones;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/SesionMedicos")
public class ControllersMedicos {
    @Autowired
    ServiceMedico usuarioService;
   
    @Autowired
    JwtProvider jwt;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    PDFservice pdfService; 
            
    @Autowired
    ServiceMedico serviceMedico;
    
    @PostMapping("/Registro")
 public ResponseEntity<?>Registrar(@RequestBody Medico medico) {
     try{
         String contra= medico.getContraseña();
         System.out.println("contra"+ contra);
     serviceMedico.AñadirYVerificarMedico(medico);
     return ResponseEntity.ok("Registrado con exito");
     }catch(Exception e){
         System.out.println("error "+e.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     }
 }
 
 @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody MedicoDTO medico){
        try{
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

        String tokenUsername= jwt.generateToken(medicoDetails);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("token", tokenUsername));
        }catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas."+e.getMessage());
        }
    }
    
    @PostMapping("/ActualizarCita")
    public ResponseEntity<?>Actualizar(@RequestBody ActualizarCitasDTO id, Principal principal){
        try {
            pdfService.GenerarPDF(id, principal);
            return ResponseEntity.ok().build();
        }catch(ResponseStatusException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
 
}
