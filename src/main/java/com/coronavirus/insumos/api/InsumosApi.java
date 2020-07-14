package com.coronavirus.insumos.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.coronavirus.insumos.dto.AprobarTicketRequest;
import com.coronavirus.insumos.dto.CancelarTicketRequest;
import com.coronavirus.insumos.dto.CrearTicketDTO;
import com.coronavirus.insumos.dto.LoginRequest;
import com.coronavirus.insumos.dto.RechazarTicketRequest;
import com.coronavirus.insumos.modelo.Usuario;

@Path("insumos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface InsumosApi {

	@GET
	@Path("/isAlive")
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
	@PreAuthorize("hasRole('ROLE_USER')")
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
	
	@GET
	@Path("/ticket/todos")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response obtenerTodosLosTickets();
	
	@GET
	@Path("/ticket/enviados")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response obtenerTicketsEnviados();
	
	@GET
	@Path("/ticket/rechazados")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response obtenerTicketsRechazados();
	
	@POST
	@Path("/ticket/aprobar")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Produces(MediaType.APPLICATION_JSON)
	Response aprobarTicket(AprobarTicketRequest request);
	

}
