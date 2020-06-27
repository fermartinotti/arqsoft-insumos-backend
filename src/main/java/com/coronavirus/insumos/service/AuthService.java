package com.coronavirus.insumos.service;

import java.util.Optional;

import com.coronavirus.insumos.dto.LoginRequest;
import com.coronavirus.insumos.dto.LoginResponse;
import com.coronavirus.insumos.modelo.Usuario;

import io.jsonwebtoken.Claims;

public interface AuthService {

	public void crearUsuario(Usuario usuario) throws Exception;

	public Optional<Usuario> usuarioByEmail(String email);
	
	public LoginResponse login(LoginRequest request) throws Exception;
	
	public Claims decodificarToken(String jwt);
}
