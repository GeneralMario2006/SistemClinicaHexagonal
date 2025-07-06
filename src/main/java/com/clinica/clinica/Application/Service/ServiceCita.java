package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryCita;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryMedicos;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryPacientes;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceCita {
    RepositoryCita repositoryCitas;
    RepositoryMedicos medicoRepository;
    RepositoryPacientes repositoryPaciente;
    

    public ServiceCita(RepositoryCita repositoryCitas, RepositoryMedicos medicoRepository, RepositoryPacientes repositoryPaciente) {
        this.repositoryCitas = repositoryCitas;
        this.repositoryPaciente= repositoryPaciente;
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
    
    public List<CitasDomain> listarCitas(String doctorEmail, String correoUrl) {
        return repositoryCitas.EnlistarCitas(doctorEmail, correoUrl);
    }
    
    public Object[] CitasDelDia(String correo){
        return repositoryCitas.CitasDelDia(correo);
    }

}

