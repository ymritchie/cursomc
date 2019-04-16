package com.ritchie.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ritchie.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public static void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instante) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instante);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		
		pagto.setDataVencimento(cal.getTime());
	}

}
