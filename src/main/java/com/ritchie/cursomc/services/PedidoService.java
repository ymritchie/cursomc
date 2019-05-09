package com.ritchie.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ritchie.cursomc.domain.Cliente;
import com.ritchie.cursomc.domain.ItemPedido;
import com.ritchie.cursomc.domain.PagamentoComBoleto;
import com.ritchie.cursomc.domain.Pedido;
import com.ritchie.cursomc.domain.enums.EstadoPagamento;
import com.ritchie.cursomc.repositories.ItemPedidoRepository;
import com.ritchie.cursomc.repositories.PagamentoRepository;
import com.ritchie.cursomc.repositories.PedidoRepository;
import com.ritchie.cursomc.security.UserSS;
import com.ritchie.cursomc.services.exceptions.AuthorizationException;
import com.ritchie.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) {
		Pedido obj = repository.findOne(id);
		
		if (obj == null) {
			throw new ObjectNotFoundException("Pedido não encontrado para o ID " + id + " na classe " + Pedido.class.getName()); 
		}
		return obj; 
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto =  (PagamentoComBoleto) obj.getPagamento();
			BoletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0D);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		
		return repository.findByCliente(cliente, pageRequest);
	}
	
}

