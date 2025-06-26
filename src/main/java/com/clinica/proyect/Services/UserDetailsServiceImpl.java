package com.clinica.proyect.Services;

import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.Entitys.Paciente;
import com.clinica.proyect.Repositorys.RepositoryMedico;
import com.clinica.proyect.Repositorys.RepositoryPaciente;
import com.clinica.proyect.Security.MedicoDetails;
import com.clinica.proyect.Security.PacienteDetails;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RepositoryMedico medicoRepository;

    @Autowired
    RepositoryPaciente pacienteRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<Medico> medico = medicoRepository.findByCorreoInstitucional(correo);
        if (medico.isPresent()) {
            return new MedicoDetails(medico.get());
        }

        Optional<Paciente> paciente = pacienteRepository.findByCorreo(correo);
        if (paciente.isPresent()) {
            return new PacienteDetails(paciente.get());
        }

        throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
    }
}

