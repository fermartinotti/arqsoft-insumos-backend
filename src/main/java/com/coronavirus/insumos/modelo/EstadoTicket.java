package com.coronavirus.insumos.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.coronavirus.insumos.utils.MiLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@JsonIgnoreProperties(value = {"new"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Enviado.class, name= "Enviado"),
	@Type(value = Cancelado.class, name= "Cancelado"),
	@Type(value = Rechazado.class, name= "Rechazado"),
	@Type(value = Aprobado.class, name= "Aprobado"),
			
})
public abstract class EstadoTicket extends AbstractPersistable<Long> implements Comparable<EstadoTicket>{

	@Column
	@JsonSerialize(using= MiLocalDateTimeSerializer.class)
	public LocalDateTime fecha = LocalDateTime.now();

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fechaCreacion) {
		this.fecha = fechaCreacion;
	}
	
	 @Override
     public int compareTo(EstadoTicket e) {
         if (fecha.isBefore(e.fecha)) {
             return -1;
         }
         if (fecha.isAfter(e.fecha)) {
             return 1;
         }
         return 0;
     }
	
}
