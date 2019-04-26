package com.ritchie.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		Produto obj = repository.findOne(id);
		
		if (obj == null) {
			throw new ObjectNotFoundException("Produto não encontrado para o ID " + id + " na classe " + Produto.class.getName());
		}
		return obj;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAll(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
}

