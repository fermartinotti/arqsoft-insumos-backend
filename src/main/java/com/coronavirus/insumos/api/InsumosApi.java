package com.coronavirus.insumos.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.access.prepost.PreAuthorize;

import com.coronavirus.insumos.dto.CancelarTicketRequest;
import com.coronavirus.insumos.dto.CrearTicketDTO;
import com.coronavirus.insumos.dto.LoginRequest;
import com.coronavirus.insumos.dto.RechazarTicketRequest;
import com.coronavirus.insumos.modelo.Usuario;

@Path("insumos")
public interface InsumosApi {

	@GET
	@Path("/isAlive")
	@PreAuthorize("hasRole('ROLE_USER')")
	Response isAlive();
	
	@POST
	@Path("/auth/registro")
	@Produces(MediaType.APPLICATION_JSON)
	Response crearUsuario(Usuario usuario);
	
	@GET
	@Path("/auth/validarEmail")
	@Produces(MediaType.APPLICATION_JSON)
	Response emailValido(@QueryParam("email") String email);
	
	@POST
	@Path("/auth/login")
	@Produces(MediaType.APPLICATION_JSON)
	Response login(LoginRequest request);

	@POST
	@Path("/ticket/nuevo")
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasRole('ROLE_USER')")
	Response crearTicket(CrearTicketDTO ticketDTO);
	
	@GET
	@Path("/ticket/misTickets")
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasRole('ROLE_USER')")
	Response misTickets();

	@POST
	@Path("/ticket/cancelarTicket")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Produces(MediaType.APPLICATION_JSON)
	Response cancelarTicket(CancelarTicketRequest request);
	
	@GET
	@Path("/ticket/areas")
	@Produces(MediaType.APPLICATION_JSON)
	Response obtenerAreas();
	
	@POST
	@Path("/ticket/rechazarTicket")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response cancelarTicket(RechazarTicketRequest request);
	
	@GET
	@Path("/proveedores/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response obtenerProveedores();
	
	// AprobarTickjet (ticketid, proveedor).

}
