package com.clinica.proyect.Repositorys;

import com.clinica.proyect.Entitys.Paciente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPaciente extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDui(Long dui);
    Optional<Paciente> findByCorreo(String correo);
}
