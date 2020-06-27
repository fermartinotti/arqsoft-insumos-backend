package com.coronavirus.insumos.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.dto.CancelarTicketRequest;
import com.coronavirus.insumos.dto.CrearTicketDTO;
import com.coronavirus.insumos.dto.LoginRequest;
import com.coronavirus.insumos.dto.LoginResponse;
import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Insumo;
import com.coronavirus.insumos.modelo.Ticket;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.InsumoRepository;
import com.coronavirus.insumos.service.AreaService;
import com.coronavirus.insumos.service.AuthService;
import com.coronavirus.insumos.service.TicketService;
import com.coronavirus.insumos.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.Claims;


@Service
public class InsumosApiImpl implements InsumosApi {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private InsumoRepository insumoRepository;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	HttpServletRequest request;
	

	@Override
	public Response isAlive() {
		return Response.ok("its alive").build();
	}

	@Override
	public Response crearUsuario(Usuario usuario) {
		ObjectNode objectNode = new ObjectMapper().createObjectNode();
		try {
			usuario.setRole("ROLE_USER");
			authService.crearUsuario(usuario);
			return Response.ok(usuario).build();
		} catch (Exception e) {
			e.printStackTrace();
			objectNode.put("Error", e.getMessage());
			return Response.status(400).entity(objectNode.toString()).build();
		}
	}

	@Override
	public Response emailValido(String email) {
		Optional<Usuario> usuario = authService.usuarioByEmail(email);
		
		if(usuario.isPresent()) {
			return Response.status(400).entity("Error, el email ya se encuentra en uso").build();
		}else {
			return Response.ok().build();
		}
	}

	@Override
	public Response login(LoginRequest request) {
		ObjectNode objectNode = new ObjectMapper().createObjectNode();
		try {
			LoginResponse response = authService.login(request);
			return Response.ok(response).build();
		}catch(Exception e) {
			e.printStackTrace();
			objectNode.put("Error", e.getMessage());
			return Response.status(400).entity(objectNode.toString()).build();
		}
	}

	@Override
	public Response crearTicket(CrearTicketDTO ticketDTO) {
		ObjectNode objectNode = new ObjectMapper().createObjectNode();
		try {
			Usuario usuario = this.obtenerUsuarioLoggeado();
			Insumo insumo = ticketDTO.getInsumo();
			
			insumoRepository.save(insumo);
			Area area = areaService.getById(ticketDTO.getIdArea());
			
			Ticket ticket = ticketService.crearTicket(usuario, insumo, area);

			return Response.status(200).entity(ticket).build();
		}catch(Exception e) {
			e.printStackTrace();
			objectNode.put("Error", e.getMessage());
			return Response.status(400).entity(objectNode.toString()).build();
		}
		
		
	}

	@Override
	public Response misTickets() {
		Usuario usuario = this.obtenerUsuarioLoggeado();
		
		List<Ticket> tickets = this.ticketService.obtenerTicketByUsuario(usuario);
		return Response.status(200).entity(tickets).build();
	}

	@Override
	public Response cancelarTicket(CancelarTicketRequest request) {
		ObjectNode objectNode = new ObjectMapper().createObjectNode();
		try {
			Usuario usuario = this.obtenerUsuarioLoggeado();
			Ticket ticket = ticketService.cancelarTicket(request.getIdTicket(), usuario);
			return Response.ok(ticket).build();
		} catch (Exception e) {
			objectNode.put("Error ", e.getMessage());
			return Response.status(400).entity(objectNode.toString()).build();
		}
	}
	
	
	private Usuario obtenerUsuarioLoggeado() {
		String token = request.getHeader("Authorization");
		Claims claims = authService.decodificarToken(token);
		String email = claims.getSubject();
		Usuario usuario = usuarioService.obtenerUsuarioByEmail(email);
		return usuario;
	}

	@Override
	public Response obtenerAreas() {
		List<Area> areas = this.areaService.obtenerAreas();
		return Response.status(200).entity(areas).build();
	}
	
	
	
}
