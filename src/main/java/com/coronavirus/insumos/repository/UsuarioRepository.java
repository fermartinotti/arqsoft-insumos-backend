package com.coronavirus.insumos.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coronavirus.insumos.modelo.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u where u.email =?1")
	public Usuario getUsuarioByEmail(String email);
	
	@Query("SELECT u FROM Usuario u where u.email =?1 and u.password =?2")
	public Usuario getByUserYPasw(String email, String password);

}
