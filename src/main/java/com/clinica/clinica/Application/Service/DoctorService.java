/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.Application.Service;

import com.clinica.clinica.domain.MedicoDomain;
import com.clinica.clinica.domain.RepositorysDomain.RepositoryMedicos;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mr587
 */
@Service
public class DoctorService {
    RepositoryMedicos repoMedico;

    public DoctorService(RepositoryMedicos repoMedico) {
        this.repoMedico = repoMedico;
    }
    
    public MedicoDomain SaveDoctor(MedicoDomain medicoDomain, String nameFile, MultipartFile file) {
       return repoMedico.AgregarMedico(medicoDomain, file);
    }
}
