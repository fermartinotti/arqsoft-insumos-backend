package com.coronavirus.insumos.service;

import java.util.List;
import java.util.Optional;

import com.coronavirus.insumos.modelo.Area;

public interface AreaService {

	public List<Area> obtenerAreas();
	
	public Area getById(Long id);
	
}
