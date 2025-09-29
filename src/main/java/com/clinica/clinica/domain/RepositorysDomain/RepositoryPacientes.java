/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.PacientesDomain;


public interface RepositoryPacientes {
    public PacientesDomain AgregarPaciente(PacientesDomain paciente);
    public PacientesDomain BuscarPacientePorCorreo(String correo);
    public PacientesDomain FindByDui(Long dui);
    
}
