package com.coronavirus.insumos.modelo;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@JsonIgnoreProperties(value = {"new", "id"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@Type(value = Mascara.class, name= "Mascara"),
		@Type(value = Barbijo.class, name= "Barbijo"),
		@Type(value = Guante.class, name= "Guante"),
		@Type(value = Respirador.class, name= "Respirador"),
		@Type(value = Medicamento.class, name= "Medicamento")
		}
)
public abstract class Insumo extends AbstractPersistable<Long>{

}
