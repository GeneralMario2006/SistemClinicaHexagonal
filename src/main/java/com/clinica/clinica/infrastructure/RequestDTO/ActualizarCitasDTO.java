package com.clinica.clinica.infrastructure.RequestDTO;


public class ActualizarCitasDTO {
    String diagnostico, medicamentos, observacion;
    Long idCita;
    
    public ActualizarCitasDTO() {
    }

    public ActualizarCitasDTO(Long idCita, String diagnostico, String medicamentos, String observacion) {
        this.diagnostico = diagnostico;
        this.idCita= idCita;
        this.medicamentos = medicamentos;
        this.observacion = observacion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }
    
    
}
