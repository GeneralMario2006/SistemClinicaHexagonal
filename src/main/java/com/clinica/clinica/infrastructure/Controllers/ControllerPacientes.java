package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.clinica.Application.Service.PatientService;
import com.clinica.clinica.domain.PacientesDomain;
import com.clinica.clinica.infrastructure.RequestDTO.PacienteDTO;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.JWT.JwtProvider;
import com.clinica.clinica.infrastructure.Mappers.MapperPaciente;
import com.clinica.clinica.infrastructure.RequestDTO.RequestPacientes;
import com.clinica.clinica.infrastructure.Security.PacienteDetails;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/Pacientes")
public class ControllerPacientes {
    
    @Autowired
    PatientService pacienteService;
    
    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    JwtProvider jwt;
    
    @Autowired
    MapperPaciente mapper;
    
    @PostMapping("/CrearPaciente")
    @PreAuthorize("hasRole('Medico')")
    public ResponseEntity<?> CrearPaciente(@RequestBody RequestPacientes paciente) {
        try{
           PacientesDomain pacienteDomain= mapper.RequestToDomain(paciente);
        pacienteService.SavePatient(pacienteDomain);
        return ResponseEntity.ok("LISTO");
        }catch(Exception e) {
           return ResponseEntity.badRequest().body("error: "+e.getMessage());
        }
    }
    
    @PostMapping("/Login")
public ResponseEntity<?> loginPaciente(@RequestBody PacienteDTO paciente) {
    try {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        paciente.getCorreo(),
                        paciente.getContraseña()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        boolean tieneRolPaciente = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_PACIENTE"));

        if (!tieneRolPaciente) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene el rol requerido.");
        }

        String token = jwt.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("token", token));

    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Credenciales incorrectas: " + e.getMessage());
    }
}

}
