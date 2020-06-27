package com.coronavirus.insumos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.modelo.Barbijo;
import com.coronavirus.insumos.modelo.Guante;
import com.coronavirus.insumos.modelo.Mascara;
import com.coronavirus.insumos.modelo.Medicamento;
import com.coronavirus.insumos.modelo.Proveedor;
import com.coronavirus.insumos.modelo.Usuario;
import com.coronavirus.insumos.repository.AreaRepository;
import com.coronavirus.insumos.repository.InsumoRepository;
import com.coronavirus.insumos.repository.ProveedorRepository;
import com.coronavirus.insumos.repository.UsuarioRepository;


@Component
public class StartUp implements ApplicationRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private InsumoRepository InsumoRepository;
	
	@Autowired
	private ProveedorRepository proveedorRepository;
	
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
	
		//Se crean insumos
		Barbijo barbijo = new Barbijo();
		Guante guante = new Guante();
		Mascara mascara = new Mascara();
		Medicamento medicamento = new Medicamento("paracetamol");
		
		InsumoRepository.save(barbijo);
		InsumoRepository.save(guante);
		InsumoRepository.save(mascara);
		InsumoRepository.save(medicamento);
		
		//Creo proveedores
		Proveedor proveedor1 = new Proveedor("Proveedor 1");
		proveedor1.setInsumo(barbijo);
		Proveedor proveedor2 = new Proveedor("Proveedor 2");
		proveedor2.setInsumo(guante);
		Proveedor proveedor3 = new Proveedor("Proveedor 3");
		proveedor3.setInsumo(mascara);
		Proveedor proveedor4 = new Proveedor("Proveedor 4");
		proveedor4.setInsumo(medicamento);
		proveedor4.setInsumo(mascara);
		
		proveedorRepository.save(proveedor1);
		proveedorRepository.save(proveedor2);
		proveedorRepository.save(proveedor3);
		proveedorRepository.save(proveedor4);
		
		
	}

}
