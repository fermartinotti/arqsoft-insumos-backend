package com.coronavirus.insumos.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.coronavirus.insumos.utils.MiLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@JsonIgnoreProperties(value = {"new"})
public class Ticket extends AbstractPersistable<Long>{

	@JoinColumn(name="cliente")
	@ManyToOne(fetch = FetchType.EAGER)
	Usuario cliente;
	
	@JoinColumn(name="insumo")
	@ManyToOne(fetch = FetchType.EAGER)
	Insumo insumo;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="estados")
	List<EstadoTicket> estados = new ArrayList<EstadoTicket>();
	
	@JoinColumn
	@ManyToOne(fetch = FetchType.EAGER)
	Area area;
	
	public Ticket(){};
	
	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}
	
	public Ticket(Usuario cliente, Insumo insumo) {
		this.cliente = cliente;
		this.insumo = insumo;
	}
	
	public List<EstadoTicket> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoTicket> estados) {
		this.estados = estados;
	}

	public void setEstado(EstadoTicket estado) {
		this.estados.add(estado);
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
}
