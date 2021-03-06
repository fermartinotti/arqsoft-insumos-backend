package com.coronavirus.insumos.service;

import java.util.List;
import java.util.Optional;

import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Insumo;
import com.coronavirus.insumos.modelo.Ticket;
import com.coronavirus.insumos.modelo.Usuario;

public interface TicketService {

	public Ticket crearTicket(Usuario usuario, Insumo insumo, Area area);
	
	public List<Ticket> obtenerTicketByUsuario(Usuario usuario);
	
	public Optional<Ticket> getTicketById(Long id);
	
	public Ticket cancelarTicket(Long id, Usuario usuario);
	
	public Ticket rechazarTicket(Long id, String motivo);
	
	public List<Ticket> obtenerTodos();
	
	public List<Ticket> obtenerTicketsEnviados();

	public Ticket AprobarTicket(Long ticketId, Long proveedorId);

	public List<Ticket> obtenerTicketsRechazados();
}
