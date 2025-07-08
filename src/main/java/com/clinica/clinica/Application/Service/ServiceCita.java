package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryMedicos;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceCita {
    RepositoryCita repositoryCitas;
    RepositoryMedicos medicoRepository;
    

    public ServiceCita(RepositoryCita repositoryCitas, RepositoryMedicos medicoRepository) {
        this.repositoryCitas = repositoryCitas;
        this.medicoRepository = medicoRepository;
    }

    public void addCita(CitasDomain cita) {
    repositoryCitas.AvaileableDateAndDoctor(medicoRepository.FindById(cita.getMedico().getId()), cita.getFechaCita());
    repositoryCitas.CrearCita(cita);
}
    /*
    public void CancelarCita(Long id) {
        Citas cancelar= repositoryCitas.findById(id).get();
        cancelar.setEstado("Cancelado");
        repositoryCitas.save(cancelar);
    }
*/
    public void AceptarCita(UpdateCitaDto domainDto, String emailPrincipal) {
        if (domainDto.getDiagnostico().isBlank() || domainDto.getMedicamentos().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hay campos inválidos.");
            }
        repositoryCitas.GenerarPdf(domainDto, emailPrincipal);
    }
    
    public List<CitasDomain> listarCitas(String doctorEmail, String correoUrl) {
        return repositoryCitas.EnlistarCitas(doctorEmail, correoUrl);
    }
    
    public Object[] CitasDelDia(String correo){
        return repositoryCitas.CitasDelDia(correo);
    }

}

