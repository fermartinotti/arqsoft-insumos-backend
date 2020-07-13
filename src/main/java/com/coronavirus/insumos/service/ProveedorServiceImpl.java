package com.coronavirus.insumos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coronavirus.insumos.modelo.Proveedor;
import com.coronavirus.insumos.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService{

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Override
	public List<Proveedor> obtenerProveedores() {
		return (List<Proveedor>) proveedorRepository.findAll();
	}
}
