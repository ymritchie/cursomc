package com.ritchie.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ritchie.cursomc.domain.Categoria;
import com.ritchie.cursomc.domain.Produto;
import com.ritchie.cursomc.repositories.CategoriaRepository;
import com.ritchie.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository; ;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000D);
		Produto p2 = new Produto(null, "Impressora", 800D);
		Produto p3 = new Produto(null, "Mouse", 80D);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}

}

