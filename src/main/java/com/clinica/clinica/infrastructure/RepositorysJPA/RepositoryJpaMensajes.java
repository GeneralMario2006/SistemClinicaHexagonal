package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.infrastructure.Entitys.Mensajes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryJpaMensajes extends JpaRepository<Mensajes, Long>{
    
@Query("SELECT m.contenido, m.remitente FROM Mensajes m WHERE m.medico.correoInstitucional = :correoMedico AND m.paciente.correo = :correoPaciente")
List<Object> findMensajesEntrePacienteYMedico(@Param("correoPaciente") String correoPaciente, @Param("correoMedico") String correoMedico);


}
