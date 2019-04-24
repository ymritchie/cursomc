package com.ritchie.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ritchie.cursomc.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
