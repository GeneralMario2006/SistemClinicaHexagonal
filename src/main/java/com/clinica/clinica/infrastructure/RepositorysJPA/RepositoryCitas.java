package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.infrastructure.Entitys.Citas;
import com.clinica.clinica.infrastructure.Entitys.Medico;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCitas extends JpaRepository<Citas, Long>{
    boolean existsByMedicoAndFechaCita(Medico medico, LocalDateTime horaYfecha);
    List<Citas> findByMedico_CorreoInstitucional(String correo);
List<Citas> findByPaciente_CorreoOrderByFechaCitaAsc(String correo);    
    List<Citas> findByPaciente_Correo(String correo);
    
@Query("SELECT c.id, c.estado, c.fechaCita, c.motivo, c.paciente.nombre FROM Citas c WHERE DATE(c.fechaCita) = CURRENT_DATE AND c.estado = :estado AND c.medico = :medico")
Object[] citasDelDia(@Param("estado") String estado, @Param("medico") Medico medico);



}
