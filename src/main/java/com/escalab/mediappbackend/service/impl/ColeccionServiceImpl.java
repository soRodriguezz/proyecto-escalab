package com.escalab.mediappbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalab.mediappbackend.model.ColeccionModel;
import com.escalab.mediappbackend.repo.IColeccionRepo;
import com.escalab.mediappbackend.service.IColeccionService;

@Service
public class ColeccionServiceImpl implements IColeccionService {
	
	@Autowired
	private IColeccionRepo repo;
	
	@Override
	public ColeccionModel registrar(ColeccionModel obj) {
		return repo.save(obj);
	}
	
	@Override
	public ColeccionModel modificar(ColeccionModel obj) {
		return repo.save(obj);
	}
	
	@Override
	public List<ColeccionModel> listar() {
		return repo.findAll();
	}
	
	@Override
	public ColeccionModel leerPorId(Integer id) {
		Optional<ColeccionModel> op = repo.findById(id);
		return op.isPresent() ? op.get() : new ColeccionModel();
	}
	
	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}
}
