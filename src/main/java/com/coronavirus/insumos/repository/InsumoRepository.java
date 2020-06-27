package com.coronavirus.insumos.repository;

import org.springframework.data.repository.CrudRepository;

import com.coronavirus.insumos.modelo.Insumo;

public interface InsumoRepository extends CrudRepository<Insumo, Long> {

}
