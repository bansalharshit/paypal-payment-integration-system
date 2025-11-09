package com.hulkhiretech.payments.service.impl;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.constant.Constant;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.paypal.res.Amount;
import com.hulkhiretech.payments.paypal.res.ExperienceContext;
import com.hulkhiretech.payments.paypal.res.OrderRequest;
import com.hulkhiretech.payments.paypal.res.PaymentSource;
import com.hulkhiretech.payments.paypal.res.Paypal;
import com.hulkhiretech.payments.paypal.res.PurchaseUnit;
import com.hulkhiretech.payments.service.TokenService;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

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
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set headers PayPal-Request-Id => UUID
		String uuid = java.util.UUID.randomUUID().toString();
		headers.add("PayPal-Request-Id", uuid);

		Amount amount = new Amount();
		amount.setCurrencyCode("USD");
		amount.setValue("1.50");
		
		PurchaseUnit purchaseUnitRequest = new PurchaseUnit();
		purchaseUnitRequest.setAmount(amount);
		
		ExperienceContext experienceContext = new ExperienceContext();
		experienceContext.setPaymentMethodPreference("IMMEDIATE_PAYMENT_REQUIRED");
		experienceContext.setLandingPage("LOGIN");
		experienceContext.setShippingPreference("NO_SHIPPING");
		experienceContext.setUserAction("PAY_NOW");
		experienceContext.setReturnUrl("https://example.com/return");
		experienceContext.setCancelUrl("https://example.com/cancel");
		
		Paypal paypal = new Paypal();
		paypal.setExperienceContext(experienceContext);
		
		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setPaypal(paypal);
		
		//Final order request
		OrderRequest createOrderReq = new OrderRequest();
		createOrderReq.setIntent("CAPTURE");
		createOrderReq.setPurchaseUnits(Collections.singletonList(purchaseUnitRequest));
		createOrderReq.setPaymentSource(paymentSource);
		
		// Convert to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String requestAsJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createOrderReq);
			log.info("Create Order Request JSON: {}", requestAsJson);
			return requestAsJson;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.error("Error converting Create Order Request to JSON", e);
			throw new RuntimeException("Error converting Create Order Request to JSON", e);
		}
	}
}
