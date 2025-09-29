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
    Page<Citas> findByPaciente_Correo(String correo, Pageable page);
    
@Query("SELECT c.id, c.estado, c.fechaCita, c.motivo, c.paciente.nombre FROM Citas c WHERE DATE(c.fechaCita) = CURRENT_DATE AND c.estado = :estado AND c.medico = :medico")
List<Object[]> citasDelDia(@Param("estado") String estado, @Param("medico") Medico medico);


 // Total de citas en el mes actual
    @Query("SELECT COUNT(c) FROM Citas c WHERE MONTH(c.fechaCita) = MONTH(CURRENT_DATE) AND YEAR(c.fechaCita) = YEAR(CURRENT_DATE)")
    Long contarCitasDelMes();

    // Citas completadas en el mes actual
    @Query("SELECT COUNT(c) FROM Citas c WHERE c.estado = 'COMPLETADA' AND MONTH(c.fechaCita) = MONTH(CURRENT_DATE) AND YEAR(c.fechaCita) = YEAR(CURRENT_DATE)")
    Long contarCitasCompletadasDelMes();

    // Citas canceladas en el mes actual
    @Query("SELECT COUNT(c) FROM Citas c WHERE c.estado = 'CANCELADO' AND MONTH(c.fechaCita) = MONTH(CURRENT_DATE) AND YEAR(c.fechaCita) = YEAR(CURRENT_DATE)")
    Long contarCitasCanceladasDelMes();
    
    @Query("SELECT c.medico.especialidad, COUNT(c) FROM Citas c WHERE MONTH(c.fechaCita)=MONTH(CURRENT_DATE) AND YEAR(c.fechaCita)= YEAR(CURRENT_DATE) GROUP BY c.medico.especialidad")
    List<Object[]> contarPorEspecialidad();
    
    Optional<Citas> findByOrderId(String orderId);
}
