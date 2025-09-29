package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.clinica.Application.Service.PayPalService;
import com.clinica.clinica.Application.Service.ServiceCita;
import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.infrastructure.RequestDTO.CitaDTO;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import com.clinica.clinica.infrastructure.Responses.TiposConsultas;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Citas")
public class ControllerCitas {

    @Autowired
    ServiceCita serviceCita;

    @Autowired
    MapperCitas mapperCitas;
    
    @Autowired
    PayPalService payPal;

    @PostMapping("/CrearCita")
    public ResponseEntity<?> CrearCita(@RequestBody CitaDTO dto, @RequestParam String consulta)throws IOException {
        try {
            CitasDomain citaDomain = mapperCitas.CitasDtoToDomain(dto);
            Map<String, String> response = payPal.CreateOrder(consulta);
            String order= response.get("orderId");
            System.out.println(order);
            citaDomain.setOrderId(order);
            serviceCita.addCita(citaDomain);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/VerCitas/{correo}")
    public ResponseEntity<?> listarCitas(
            @PathVariable String correo,
            Principal principal,
            @RequestParam int pagina,
            @RequestParam int tamaño) {
        System.out.println("Accediendo a listar citas");
        try {
            String correoAutenticado = principal.getName();
            Page<CitasDomain> citasPaginadas = serviceCita.listarCitas(correoAutenticado, correo, tamaño, pagina);
            return ResponseEntity.ok(citasPaginadas);
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado." + e.getMessage());

        }
    }

    @PutMapping("/Cancelar/{id}")
    public ResponseEntity<?> ActualizarCitasCancelada(@PathVariable Long id) {
        serviceCita.CancelarCita(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/CitasDelDia")
    public ResponseEntity<?> VerCitasDelDia(Principal principal) {
        String correo = principal.getName();
        List<Object[]> citasPaginadas = serviceCita.CitasDelDia(correo);
        if (citasPaginadas.isEmpty()) {
            return ResponseEntity.ok("No hay citas para este dia.");
        }
        return ResponseEntity.ok(citasPaginadas);

    }

    @GetMapping("/ResumenDelMes")
    @PreAuthorize("hasRole('Medico')")
    public ResponseEntity<?> ResumenDelMes() {
        return ResponseEntity.ok(serviceCita.ResumenMensual());
    }

    @GetMapping("/CitasPorEspecialidad")
    public ResponseEntity<?> ResumenPorEspecialidad() {

        return ResponseEntity.ok(serviceCita.ResumenPorEspecialidad());
    }

    @GetMapping("/GetConsultas")
    public List<String> obtenerEstadosPedido() {
        // Convierte los valores del enum a lista de strings
        return Arrays.asList(TiposConsultas.values())
.stream()
.map(Enum::name) // devuelve el nombre de cada enum
.toList();
        
    }
}
