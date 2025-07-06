/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.proyect.DTOS.MensajeDTO;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*

@RestController
@RequestMapping("/Mensajes")
public class MensajeController {
    
    @Autowired
    ServicesMensajes mensajeService;
    
    @PostMapping("EnviarMensajeComoPaciente")
    public ResponseEntity<?>EnviarMsjPaciente(@RequestBody MensajeDTO dto, Principal principal) {
        mensajeService.EnviaMensajePaciente(principal, dto);
        return ResponseEntity.ok(Map.of("mensaje", "MensajeEnviado"));
    }
    
    @GetMapping("/VerMensajesMedicos/{correoMedico}")
    public ResponseEntity<?>VerMensajesDeMedicos(@PathVariable("correoMedico") String correo, Principal principal) {
        return ResponseEntity.ok(mensajeService.VerMensajesMedicos(principal, correo));
    }
    
    //MEDICOS
    @PostMapping("/EnviarMensajeComoMedico")
    public ResponseEntity<?>EnviarMsjMedico(@RequestBody MensajeDTO dto, Principal principal) {
        mensajeService.EnviaMensajeMedico(principal, dto);
        return ResponseEntity.ok().body("MensajeEnviado");
    }
    
    @GetMapping("/VerMensajesPacientesDesdeMedico/{correo}")
    public ResponseEntity<?>VerMensajesDePacientes(@PathVariable String correo, Principal principal) {
        return ResponseEntity.ok(mensajeService.VerMensajesPacientes(principal, correo));
    }
    
}
*/