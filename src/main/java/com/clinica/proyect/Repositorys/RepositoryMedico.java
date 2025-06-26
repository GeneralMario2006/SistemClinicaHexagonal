package com.clinica.proyect.Repositorys;

import com.clinica.proyect.Entitys.Medico;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryMedico extends JpaRepository<Medico, Long> {
Optional<Medico> findByCorreoInstitucional(String correo);  

}
