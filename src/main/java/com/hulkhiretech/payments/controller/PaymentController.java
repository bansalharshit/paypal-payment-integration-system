package com.hulkhiretech.payments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping("/payments")
	public String createOrder() {
		// TODO once the request & response is finalize, update this logic
		
		log.info("Creating order in PayPal provider service");
		
		String response = paymentService.createOrder();
		log.info("Order creation response from service: {}", response);
		
		return response;
	}
}
