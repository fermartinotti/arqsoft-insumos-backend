package com.coronavirus.insumos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coronavirus.insumos.modelo.Ticket;
import com.coronavirus.insumos.modelo.Usuario;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	@Query("SELECT t FROM Ticket t where t.cliente =?1")
	public List<Ticket> obtenerTicketByUsuario(Usuario usuario);
}
