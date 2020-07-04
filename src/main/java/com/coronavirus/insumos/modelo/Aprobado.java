package com.coronavirus.insumos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"new" , "id"})
public class Aprobado extends EstadoTicket{

	public Aprobado(){};
	
	@OneToOne
	public Proveedor proveedor;

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	public Aprobado(Proveedor proveedor){
		this.proveedor = proveedor;
	};
}
