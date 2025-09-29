/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.RepositorysJPA;

import com.clinica.clinica.infrastructure.Entitys.Paciente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mr587
 */
@Repository
public interface RepositoryPaciente extends JpaRepository<Paciente, Long>{
    Optional<Paciente> findByDui(Long dui);
    Optional<Paciente> findByCorreo(String correo);
}
