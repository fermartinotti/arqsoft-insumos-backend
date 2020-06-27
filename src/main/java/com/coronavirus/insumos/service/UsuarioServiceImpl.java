package com.coronavirus.insumos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario obtenerUsuarioByEmail(String email) {
		
		return usuarioRepository.getUsuarioByEmail(email);
	}

}
