package com.clinica.clinica.infrastructure.JWT;

import com.clinica.clinica.infrastructure.Entitys.Medico;
import com.clinica.clinica.infrastructure.Entitys.Paciente;
import com.clinica.clinica.infrastructure.RepositorysJPA.RepositoryMedico;
import com.clinica.clinica.infrastructure.RepositorysJPA.RepositoryPaciente;
import com.clinica.clinica.infrastructure.Security.MedicoDetails;
import com.clinica.clinica.infrastructure.Security.PacienteDetails;
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

