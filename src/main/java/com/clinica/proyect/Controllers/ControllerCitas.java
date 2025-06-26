package com.clinica.proyect.Controllers;

import com.clinica.proyect.DTOS.CitaDTO;
import com.clinica.proyect.Entitys.Citas;
import com.clinica.proyect.Services.ServiceCita;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/Citas")
public class ControllerCitas {
    @Autowired
    ServiceCita validarCitas;
    
    @PostMapping("/CrearCita")
    public ResponseEntity<?>CrearCita(@RequestBody CitaDTO dto) {
        try{
        validarCitas.addCita(dto);
        return ResponseEntity.ok("Cita creada.");
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: "+e.getMessage());
        }
    }
    
    @GetMapping("/VerCitas/{correo}")
public ResponseEntity<?> listarCitas(
    @PathVariable String correo,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "1") int size,
    Principal principal) {
    System.out.println("Accediendo a listar citas");
    try {
      Pageable pageable = PageRequest.of(page, size);
    Page<Citas> citasPaginadas = validarCitas.listarCitas(principal, correo, page, size);
        return ResponseEntity.ok(citasPaginadas);
    } catch (Exception e) {
        System.out.println("error" + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado." +e.getMessage());
        
    }
}

@PutMapping("/Cancelar/{id}")
public ResponseEntity<?>ActualizarCitasCancelada(@PathVariable Long id) {
    validarCitas.CancelarCita(id);
    return ResponseEntity.ok().build();
}

@GetMapping("/CitasDelDia")
public ResponseEntity<?>VerCitasDelDia(Principal principal, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "1") int size) {
    try{
    Page<Object[]> citasPaginadas = validarCitas.CitasDelDia(principal, page, size);
    return ResponseEntity.ok(citasPaginadas);
    }catch(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).build();
    }
}



}
