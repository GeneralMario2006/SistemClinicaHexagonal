package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.RepositorysDomain.PdfRepository;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryMedicos;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    
    public CitasDomain CancelarCita(Long id) {
        if (id.toString().isBlank()){
            throw new IllegalArgumentException("Proporcione un id");
        }
      return repositoryCitas.CancelarCitas(id);
    }
    
    public Page<CitasDomain> listarCitas(String doctorEmail, String correoUrl, int tamaño,int pagina) {
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by("fechaCita").descending());
        return repositoryCitas.EnlistarCitas(doctorEmail, correoUrl, pageable);
    }
    
    public List<Object[]> CitasDelDia(String correo){
        return repositoryCitas.CitasDelDia(correo);
    }
    
    public Map<String, Long>ResumenMensual() {
        return repositoryCitas.ResumenDelMes();
    }
    
    public List<Object[]>ResumenPorEspecialidad() {
        return repositoryCitas.CitasPorEspecialidad();
    }
}

