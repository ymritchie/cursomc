package com.ritchie.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.Categoria;
import com.ritchie.cursomc.domain.Cidade;
import com.ritchie.cursomc.domain.Cliente;
import com.ritchie.cursomc.domain.Endereco;
import com.ritchie.cursomc.domain.Estado;
import com.ritchie.cursomc.domain.ItemPedido;
import com.ritchie.cursomc.domain.Pagamento;
import com.ritchie.cursomc.domain.PagamentoComBoleto;
import com.ritchie.cursomc.domain.PagamentoComCartao;
import com.ritchie.cursomc.domain.Pedido;
import com.ritchie.cursomc.domain.Produto;
import com.ritchie.cursomc.domain.enums.EstadoPagamento;
import com.ritchie.cursomc.domain.enums.Perfil;
import com.ritchie.cursomc.domain.enums.TipoCliente;
import com.ritchie.cursomc.repositories.CategoriaRepository;
import com.ritchie.cursomc.repositories.CidadeRepository;
import com.ritchie.cursomc.repositories.ClienteRepository;
import com.ritchie.cursomc.repositories.EnderecoRepository;
import com.ritchie.cursomc.repositories.EstadoRepository;
import com.ritchie.cursomc.repositories.ItemPedidoRepository;
import com.ritchie.cursomc.repositories.PagamentoRepository;
import com.ritchie.cursomc.repositories.PedidoRepository;
import com.ritchie.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase () throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000D);
		Produto p2 = new Produto(null, "Impressora", 800D);
		Produto p3 = new Produto(null, "Mouse", 80D);
		Produto p4 = new Produto(null, "Mesa de Escritório", 300D);
		Produto p5 = new Produto(null, "Toalha", 50D);
		Produto p6 = new Produto(null, "Colcha", 200D);
		Produto p7 = new Produto(null, "TV true color", 1200D);
		Produto p8 = new Produto(null, "Roçadeira", 800D);
		Produto p9 = new Produto(null, "Abajour", 100D);
		Produto p10 = new Produto(null, "Pendente", 180D);
		Produto p11 = new Produto(null, "Shampoo", 90D);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));

		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().add(cat2);
		p5.getCategorias().add(cat3);
		p6.getCategorias().add(cat3);
		p7.getCategorias().add(cat4);
		p8.getCategorias().add(cat5);
		p9.getCategorias().add(cat6);
		p10.getCategorias().add(cat6);
		p11.getCategorias().add(cat7);
		
		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		List<Estado> listaEstado = Arrays.asList(est1, est2);
		List<Cidade> listaCidade = Arrays.asList(c1, c2, c3);

		estadoRepository.save(listaEstado);
		cidadeRepository.save(listaCidade);

		Cliente cli1 = new Cliente(null, "Maria ", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
		
		Cliente cli2 = new Cliente(null, "Anna Costa ", "anna@gmail.com", "52936725097", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);

		cli1.getTelefones().addAll(Arrays.asList("9999999", "8888888"));
		
		cli1.getTelefones().addAll(Arrays.asList("9999999", "8888888"));

		Endereco e1 = new Endereco(null, "Ruas Flores", "300", "Apt 303", "Jardim", "70660081", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 150", "Centro", "70660081", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida Leste", "205", null, "Centro", "70660082", cli2, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		cli1.getEnderecos().addAll(Arrays.asList(e3));

		List<Endereco> listaEndereco = Arrays.asList(e1, e2, e3);
		
		clienteRepository.save(Arrays.asList(cli1, cli2));
		enderecoRepository.save(listaEndereco);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),  cli1, e1);

		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 10:32"),  cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		List<Pedido> listaPedido = Arrays.asList(ped1, ped2);
		
		List<Pagamento> listaPagamento = Arrays.asList(pagto1, pagto2);

		pedidoRepository.save(listaPedido);
		pagamentoRepository.save(listaPagamento);

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0D, 1, 2000D);

		ItemPedido ip2 = new ItemPedido(ped1, p3, 0D, 2, 80D);

		ItemPedido ip3 = new ItemPedido(ped2, p2, 100D, 1, 800D);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));

		ped1.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p2.getItens().add(ip2);
		
		List<ItemPedido> listaItemPedido = Arrays.asList(ip1, ip2, ip3);

		itemPedidoRepository.save(listaItemPedido);
	}
}
