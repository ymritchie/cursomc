package com.ritchie.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.Categoria;
import com.ritchie.cursomc.dto.CategoriaDTO;
import com.ritchie.cursomc.repositories.CategoriaRepository;
import com.ritchie.cursomc.services.exceptions.DataIntegrityException;
import com.ritchie.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Categoria não encontrada para o ID " + id + " na classe " + Categoria.class.getName());
		}
		return obj.get();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir a categoria com produtos");
		}
		
	}

	public List<Categoria> findAll() {
		
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		Pageable  pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}

