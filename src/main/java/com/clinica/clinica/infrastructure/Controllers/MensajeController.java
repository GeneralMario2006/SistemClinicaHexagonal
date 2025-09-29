/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.clinica.Application.Service.ServiceMessage;
import com.clinica.clinica.domain.MensajesDomain;
import com.clinica.clinica.infrastructure.Mappers.MapperMensajes;
import com.clinica.clinica.infrastructure.RequestDTO.MensajeDTO;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/Mensajes")
public class MensajeController {
    @Autowired
    MapperMensajes msjMapper;
    
    @Autowired
    ServiceMessage mensajeService;
    
    @PostMapping("EnviarMensajeComoPaciente")
    public ResponseEntity<?>EnviarMsjPaciente(@RequestBody MensajeDTO dto, Principal principal) {
        try {
           String correo= principal.getName();
           MensajesDomain domain= msjMapper.MsjDtoToDomain(dto);
           
        mensajeService.EnviarMsjComoPaciente(correo,domain);
        return ResponseEntity.ok(Map.of("mensaje", "MensajeEnviado"));
        }catch(ResponseStatusException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: "+exception.getMessage());
        }
    }
    
    @GetMapping("/VerMensajesMedicos/{correoMedico}")
    public ResponseEntity<?>VerMensajesDeMedicos(@PathVariable("correoMedico") String correo, Principal principal) {
        String correoPaciente= principal.getName();
        List<Object> VerMensajesComoPacientes = mensajeService.VerMensajesComoPaciente(correoPaciente, correo);
        if(VerMensajesComoPacientes.isEmpty()) {
            return ResponseEntity.ok("No hay mensajes aun");
        }
        return ResponseEntity.ok(mensajeService.VerMensajesComoPaciente(correoPaciente, correo));
    }
    
    
    //MEDICOS
    @PostMapping("/EnviarMensajeComoMedico")
    public ResponseEntity<?>EnviarMsjMedico(@RequestBody MensajeDTO dto, Principal principal) {
        try {
            String emailPrincipal= principal.getName();
            MensajesDomain domain=msjMapper.MsjDtoToDomain(dto);
            mensajeService.enviarMessageLikeDoctor(emailPrincipal, domain);
            return ResponseEntity.ok("Enviado.");
        }catch(ResponseStatusException e) {
            return ResponseEntity.badRequest().body("Error: "+e);
        }
    }
    
    @GetMapping("/VerMensajesPacientesDesdeMedico/{correo}")
    public ResponseEntity<?>VerMensajesDePacientes(@PathVariable String correo, Principal principal) {
       try {
        String emailPrincipal= principal.getName();
           List<Object> VerMensajesComoMedicoPacientes = mensajeService.VerMensajesComoMedicoPacientes(emailPrincipal, correo);
           if (VerMensajesComoMedicoPacientes.isEmpty()) {
               return ResponseEntity.ok("No hay mensajes.");
           }
        return ResponseEntity.ok(VerMensajesComoMedicoPacientes);
        }catch(ResponseStatusException e){
            return ResponseEntity.badRequest().body("Error: "+e);
        }
    }
    
}
