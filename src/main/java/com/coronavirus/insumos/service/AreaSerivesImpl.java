package com.coronavirus.insumos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.exceptions.AreaInvalidaException;
import com.coronavirus.insumos.modelo.Area;
import com.coronavirus.insumos.repository.AreaRepository;

@Service
public class AreaSerivesImpl implements AreaService{

	@Autowired
	private AreaRepository areaRepository;
	
	@Override
	public List<Area> obtenerAreas() {
		return (List<Area>) areaRepository.findAll();
	}

	@Override
	public Area getById(Long id) {
		Optional<Area> area = areaRepository.findById(id);
		if (area.isPresent()) {
			return area.get();
		}else {
			throw new AreaInvalidaException("Area inexistente");
		}
	}

}
