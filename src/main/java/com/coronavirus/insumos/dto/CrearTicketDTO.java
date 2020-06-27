package com.coronavirus.insumos.dto;

import com.coronavirus.insumos.modelo.Insumo;

public class CrearTicketDTO {
	
	Insumo insumo;
	Long idArea;

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}
	
}
