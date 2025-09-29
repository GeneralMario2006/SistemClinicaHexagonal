/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.domain.DomainDtos.UpdateCitaDto;
import com.clinica.clinica.domain.MedicoDomain;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author mr587
 */
public interface RepositoryCita {
    public CitasDomain CrearCita(CitasDomain cita);
    public Page<CitasDomain>EnlistarCitas(String correoAutenticado, String correoUrl, Pageable page);
    public List<Object[]>CitasDelDia(String correo);
    public CitasDomain CancelarCitas(Long cancelarCita);
    public boolean AvaileableDateAndDoctor(MedicoDomain medico, LocalDateTime horaYfecha);
    public List<Object[]>CitasPorEspecialidad();
    public Map<String, Long> ResumenDelMes();
    
    public CitasDomain FindByOrderId(String orderId);
}
