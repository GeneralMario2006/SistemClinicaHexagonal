/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.domain.RepositorysDomain;

import com.clinica.clinica.domain.MensajesDomain;
import java.util.List;

/**
 *
 * @author mr587
 */
public interface MensajesRepositoryNoJpa {
   public void EnviaMensajeComoPaciente(String CorreoPrincipal, MensajesDomain MsjDomain);
   public List<Object>VerMensajesComoPacienteDeMedicos(String correoPrincipal, String correoMedico);
   public List<Object>VerMensajesComoMedicoDePacientes(String principal, String correoMedico);
   public void EnviaMensajeComoMedico(String principal, MensajesDomain dto);
}
