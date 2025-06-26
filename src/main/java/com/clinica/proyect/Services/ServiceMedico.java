package com.clinica.proyect.Services;

import com.clinica.proyect.Entitys.Medico;
import com.clinica.proyect.ValidacionesPacientes.MedicosValidaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceMedico {
@Autowired
MedicosValidaciones validarMedico;

    public void AñadirYVerificarMedico(Medico medico) {
        if (validarMedico.validarMedico(medico.getCorreoInstitucional())) {
            System.out.println("YA EXISTE");
            return;
        }
        validarMedico.añadirMedico(medico);
        
    }
}
