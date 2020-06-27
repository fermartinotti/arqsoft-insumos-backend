package com.coronavirus.insumos.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.exceptions.TicketInvalidoException;
import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Cancelado;
import com.coronavirus.insumos.modelo.Enviado;
import com.coronavirus.insumos.modelo.EstadoTicket;
import com.coronavirus.insumos.modelo.Insumo;
import com.coronavirus.insumos.modelo.Ticket;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.EstadoTicketRepository;
import com.coronavirus.insumos.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private EstadoTicketRepository estadoTicketRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public Ticket crearTicket(Usuario usuario, Insumo insumo, Area area) {
		Ticket ticket = new Ticket(usuario, insumo);
		EstadoTicket enviado = new Enviado();
		this.estadoTicketRepository.save(enviado);
		ticket.setEstado(enviado);
		ticket.setArea(area);
		
		ticketRepository.save(ticket);
		
		return ticket;
	}

	@Override
	public List<Ticket> obtenerTicketByUsuario(Usuario usuario) {
		List<Ticket> tickets = ticketRepository.obtenerTicketByUsuario(usuario);
		for (Ticket ticket : tickets) {
			Collections.reverse(ticket.getEstados());
		}
		return tickets ;
		
	}

	@Override
	public Optional<Ticket> getTicketById(Long id) {
		return ticketRepository.findById(id);
	}
	
	public Ticket cancelarTicket(Long id, Usuario usuario) {
		Optional<Ticket> OptTicket = this.getTicketById(id);
		if (OptTicket.isPresent()) {
			Ticket ticket = OptTicket.get();
			if (ticket.getCliente().equals(usuario)) {
				EstadoTicket cancelado = new Cancelado();
				estadoTicketRepository.save(cancelado);
				ticket.setEstado(cancelado);
				ticketRepository.save(ticket);
				return ticket;
			}else {
				throw new TicketInvalidoException("El ticket no pertenece a este cliente");
			}			
		}else {
			throw new TicketInvalidoException("El ticket es inexistente");	
		}
	}


}
