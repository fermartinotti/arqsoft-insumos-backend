package com.coronavirus.insumos.service;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.dto.LoginRequest;
import com.coronavirus.insumos.dto.LoginResponse;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.UsuarioRepository;
import com.coronavirus.insumos.utils.TokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private TokenProvider tokenProvider = new TokenProvider();
	
	@Override
	@Transactional
	public void crearUsuario(Usuario usuario) throws Exception {
		//Valido si existe un usuario con ese email
		if (usuarioByEmail(usuario.getEmail()).isPresent()){
			throw new Exception("Ya existe un usuario registrado con ese email.");
		}else {
			usuarioRepository.save(usuario);
		}
		
	}

	@Override
	public Optional<Usuario> usuarioByEmail(String email) {
		return Optional.ofNullable(usuarioRepository.getUsuarioByEmail(email));
	}

	@Override
	public LoginResponse login(LoginRequest request) throws Exception {
		if (credencialesValidas(request.getUsuario(), request.getPassword())){
			//Busco el usuario
			Usuario usuario = usuarioByEmail(request.getUsuario()).get();
			String role = usuario.getRole();
			String token = tokenProvider.generarJWT(usuario.getEmail(), role);
			
			LoginResponse response = new LoginResponse();
			response.setUsuario(request.getUsuario());
			response.setToken(token);	
			response.setRole(role);
			return response;
		}else {
			throw new Exception("Usuario invalido");
		}
		
	}
	
	public boolean credencialesValidas(String email, String password) {
		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.getByUserYPasw(email, password));
		return (usuario.isPresent());
	}
	
	public Claims decodificarToken(String jwt) {
		Claims claims = tokenProvider.decodeJWT(jwt);
		return claims;
	}

}
