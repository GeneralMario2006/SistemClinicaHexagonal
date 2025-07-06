/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.MedicoDomain;

/**
 *
 * @author mr587
 */
public interface RepositoryMedicos {
    public MedicoDomain AgregarMedico(MedicoDomain medico);
    public MedicoDomain FindById(Long id);
}
