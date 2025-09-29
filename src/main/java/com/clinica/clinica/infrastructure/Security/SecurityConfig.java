package com.clinica.clinica.infrastructure.Security;

import com.clinica.clinica.infrastructure.JWT.JwtFiltrer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtFiltrer jwtAuthFilter;
    
    public SecurityConfig(JwtFiltrer jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/Paypal/**").permitAll()
                .requestMatchers("/SesionMedicos/login").permitAll()
                .requestMatchers("/Pacientes/Login").permitAll()
                        .requestMatchers("/Citas/GetConsultas").permitAll()
                // Accesos especiales
                .requestMatchers(HttpMethod.GET, "/Citas/VerCitas/**").hasAnyRole("PACIENTE", "MEDICO")
                .requestMatchers(HttpMethod.POST, "/Mensajes/EnviarMensajeComoPaciente").hasRole("PACIENTE")
                .requestMatchers(HttpMethod.GET, "/Mensajes/VerMensajesMedicos/**").hasRole("PACIENTE")
                .requestMatchers(HttpMethod.GET, "/Mensajes/VerMensajesPacientesDesdeMedico/**").hasRole("MEDICO")
                .requestMatchers(HttpMethod.POST, "/Mensajes/EnviarMensajeComoMedico").hasRole("MEDICO")
                // Rutas protegidas
                
                .requestMatchers("/SesionMedicos/Registro").hasRole("MEDICO")
                .requestMatchers("/SesionMedicos/**", "/Pacientes/CrearPaciente").hasRole("MEDICO")
                .requestMatchers("/Pacientes/**").hasRole("PACIENTE")
                .requestMatchers("/Citas/**").hasRole("MEDICO")
                .requestMatchers("/SesionMedicos/GetAllDoctors").hasAnyRole("MEDICO", "PACIENTE")
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder,
            UserDetailsService uds)
            throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(uds)
                .passwordEncoder(encoder)
                .and()
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
