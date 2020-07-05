package com.coronavirus.insumos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.exceptions.TicketInvalidoException;
import com.coronavirus.insumos.modelo.Aprobado;
import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Cancelado;
import com.coronavirus.insumos.modelo.Enviado;
import com.coronavirus.insumos.modelo.EstadoTicket;
import com.coronavirus.insumos.modelo.Insumo;
import com.coronavirus.insumos.modelo.Proveedor;
import com.coronavirus.insumos.modelo.Rechazado;
import com.coronavirus.insumos.modelo.Ticket;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.EstadoTicketRepository;
import com.coronavirus.insumos.repository.ProveedorRepository;
import com.coronavirus.insumos.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private EstadoTicketRepository estadoTicketRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private ProveedorRepository proveedorRepository;
	
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
	
	public Ticket rechazarTicket(Long id, String motivo) {
		Optional<Ticket> OptTicket = this.getTicketById(id);
		if (OptTicket.isPresent()) {
			Ticket ticket = OptTicket.get();
			EstadoTicket rechazado = new Rechazado(motivo);
			estadoTicketRepository.save(rechazado);
			ticket.setEstado(rechazado);
			ticketRepository.save(ticket);
			return ticket;
		}else {
			throw new TicketInvalidoException("El ticket es inexistente");	
		}
	}

	@Override
	public List<Ticket> obtenerTodos() {
		List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
		return tickets;
	}

	@Override
	public List<Ticket> obtenerTicketsEnviados() {
		List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
		List<Ticket> retorno = new ArrayList<Ticket>();
		for(Ticket ticket : tickets) {
			EstadoTicket estadoActual = this.obtenerUltimoEstado(ticket);
			if (estadoActual instanceof Enviado) retorno.add(ticket);
		}
		return retorno;
	}

	@Override
	public Ticket AprobarTicket(Long ticketId, Long proveedorId) {
		Optional<Ticket> OptTicket = this.getTicketById(ticketId);
		Optional<Proveedor> optProveedor= this.proveedorRepository.findById(proveedorId);
		if (OptTicket.isPresent() && optProveedor.isPresent()) {
			Ticket ticket = OptTicket.get();
			Proveedor proveedor= optProveedor.get();
			if (esTicketValidoParaAprobar(ticket)){
				EstadoTicket aprobado = new Aprobado(proveedor);
				estadoTicketRepository.save(aprobado);
				ticket.setEstado(aprobado);
				ticketRepository.save(ticket);
				return ticket;
			}else {
				throw new TicketInvalidoException("Este ticket no esta en un estado valido para ser aprobado");
			}
		
		}else {
			throw new TicketInvalidoException("Ticket o proveedor inexistente");	
		}
	}
	
	private EstadoTicket obtenerUltimoEstado(Ticket ticket){
		List<EstadoTicket> estados = ticket.getEstados();
		ArrayList<EstadoTicket> retorno = new ArrayList<EstadoTicket>(estados);
		Collections.reverse(retorno);
		return retorno.get(0);
	}

	private boolean esTicketValidoParaAprobar(Ticket ticket) {
		EstadoTicket estadoActual = this.obtenerUltimoEstado(ticket);
		return (estadoActual instanceof Enviado);
	}

	@Override
	public List<Ticket> obtenerTicketsRechazados() {
		List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
		List<Ticket> retorno = new ArrayList<Ticket>();
		for(Ticket ticket : tickets) {
			EstadoTicket estadoActual = this.obtenerUltimoEstado(ticket);
			if (estadoActual instanceof Rechazado) retorno.add(ticket);
		}
		return retorno;
	}

}
