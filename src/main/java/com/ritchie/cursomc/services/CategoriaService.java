package com.ritchie.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.Categoria;
import com.ritchie.cursomc.repositories.CategoriaRepository;
import com.ritchie.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada para o ID " + id + " na classe " + Categoria.class.getName()));
	}
}

