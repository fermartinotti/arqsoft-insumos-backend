package com.coronavirus.insumos.modelo;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"new" , "id"})
public class Rechazado extends EstadoTicket{
	
	public String motivo;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public Rechazado(){};
	
	public Rechazado(String motivo) {
		this.motivo = motivo;
	}
	
}
