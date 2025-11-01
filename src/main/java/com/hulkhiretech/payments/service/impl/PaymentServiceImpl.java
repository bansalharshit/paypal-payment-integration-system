package com.hulkhiretech.payments.service.impl;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.service.TokenService;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final TokenService tokenService;
	
	@Override
	public String createOrder() {
		log.info("Creating order in PaymentServiceImpl");
		
		String accessToken = tokenService.getAccessToken();
		log.info("Access token retrieved: {}", accessToken);
		
		/* TODO 
		 	1. getAccessToken (OAuth)
			2. Call paypal createOrder
			3. Success/Failure/TimeOut - Proper response handling
			4. What to return to your calling service (payment-processing-service)
		 */
		
		return "Order Created from service - " + accessToken;
	}
	
	public String method2() {
		return "method2 called";
	}
	
	@PostConstruct
	public void init() {
		log.info("PaymentServiceImpl initialized");
	}
}
