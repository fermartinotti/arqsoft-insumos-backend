package com.coronavirus.insumos.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"new"})
public class Proveedor extends AbstractPersistable<Long> {

	@Column
	String nombre;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="insumos")
	List<Insumo> insumos = new ArrayList<Insumo>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Insumo> getInsumos() {
		return insumos;
	}

	public void setInsumos(List<Insumo> insumos) {
		this.insumos = insumos;
	}
	
	public void setInsumo(Insumo insumo) {
		this.insumos.add(insumo);
	}
	
	public Proveedor(){}
	
	public Proveedor(String nombre) {
		this.nombre = nombre;
	}
}
