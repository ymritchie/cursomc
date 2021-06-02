package com.ritchie.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.Categoria;
import com.ritchie.cursomc.domain.Produto;
import com.ritchie.cursomc.repositories.CategoriaRepository;
import com.ritchie.cursomc.repositories.ProdutoRepository;
import com.ritchie.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Produto não encontrado para o ID " + id + " na classe " + Produto.class.getName());
		}
		return obj.get();
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		Pageable pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
}

