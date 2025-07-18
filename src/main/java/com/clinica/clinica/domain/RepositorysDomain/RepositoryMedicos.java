/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.MedicoDomain;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mr587
 */
public interface RepositoryMedicos {
    public MedicoDomain AgregarMedico(MedicoDomain medico, MultipartFile file);
    public MedicoDomain FindById(Long id);
    
}
