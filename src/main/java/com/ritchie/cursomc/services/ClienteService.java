package com.ritchie.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.Cliente;
import com.ritchie.cursomc.repositories.ClienteRepository;
import com.ritchie.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para o ID " + id + " na classe " + Cliente.class.getName()));
	}
}

