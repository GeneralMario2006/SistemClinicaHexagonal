package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.DomainDtos.ResponseDoctors;
import com.clinica.clinica.domain.MedicoDomain;
import java.util.List;

public interface RepositoryMedicos {
    public MedicoDomain AgregarMedico(MedicoDomain medico);
    public MedicoDomain FindById(Long id);
    public MedicoDomain FindByNombre(String nombre);
    public List<ResponseDoctors> GetAllDoctors(); 
}
