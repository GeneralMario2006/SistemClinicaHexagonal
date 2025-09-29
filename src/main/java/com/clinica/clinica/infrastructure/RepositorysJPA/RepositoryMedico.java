package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.domain.DomainDtos.ResponseDoctors;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryMedico extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCorreoInstitucional(String correo);

    Optional<Medico> findByNombre(String nombre);

    @Query("SELECT new com.clinica.clinica.domain.DomainDtos.ResponseDoctors(m.nombre) FROM Medico AS m")
    List<ResponseDoctors> GetAllDoctors();
}
