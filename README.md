[![Build Status](https://travis-ci.org/yoLUkAsss/arq-soft-2020-java.svg?branch=master)](https://travis-ci.org/yoLUkAsss/arq-soft-2020-java)

# Backend - Insumos - Arquitectura de Software 1 2020
Backend construido en Java8 utilizando Springboot + Hibernate con una Base de datos MYSql

# Instalacion
## Descargar
```
git clone https://github.com/yoLUkAsss/arq-soft-2020-java.git
cd arq-soft-2020-java
```

## Instalar
```
mvn install
```

## RUN
```
mvn spring-boot:run
```


# API Rest
Este componente expone una API Rest con los siguientes Endpoints: 

### Auth - Metodos sin JWT
```java

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

```

### Ticket - Requiere token JWT
```java

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
```

