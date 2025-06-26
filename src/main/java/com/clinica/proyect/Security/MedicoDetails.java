/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.proyect.Security;

import com.clinica.proyect.Entitys.Medico;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author mr587
 */
public class MedicoDetails implements UserDetails{
 private String correo;
    private String contraseña;
    private String rol;
public MedicoDetails(Medico medico) {
        this.correo = medico.getCorreoInstitucional();
        this.contraseña = medico.getContraseña();
        this.rol = medico.getRol();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getPassword() {
    return contraseña;
    }

    @Override
    public String getUsername() {
return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
