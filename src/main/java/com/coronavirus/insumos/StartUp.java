package com.coronavirus.insumos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.AreaRepository;
import com.coronavirus.insumos.repository.UsuarioRepository;


@Component
public class StartUp implements ApplicationRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		// AGREGA ADMIN AUTOMATICAMENTE
		Usuario admin = new Usuario();
		admin.setEmail("admin@insumos.com.ar");
		admin.setPassword("admin123");
		admin.setRole("ROLE_ADMIN");
		usuarioRepository.save(admin);
		
		//Se settean las Areas por default
		Area atencionPacientes = new Area("Atencion de pacientes");
		Area terapiaIntensiva = new Area ("Terapia Intensiva");
		Area tecnicos = new Area ("Tecnicos");
		
		areaRepository.save(atencionPacientes);
		areaRepository.save(terapiaIntensiva);
		areaRepository.save(tecnicos);
	
		

		
	}

}
