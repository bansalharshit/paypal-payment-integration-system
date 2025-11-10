package com.hulkhiretech.payments.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.paypal.res.PaypalOrder;
import com.hulkhiretech.payments.pojo.CreateOrderReq;
import com.hulkhiretech.payments.pojo.CreateOrderRes;
import com.hulkhiretech.payments.service.TokenService;
import com.hulkhiretech.payments.service.helper.CreateOrderHelper;
import com.hulkhiretech.payments.service.interfaces.PaymentService;
import com.hulkhiretech.payments.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	
	private final TokenService tokenService;

	private final HttpServiceEngine httpServiceEngine;

	private final JsonUtil jsonUtil;

	private final CreateOrderHelper createOrderHelper;

	@Override
	public CreateOrderRes createOrder(CreateOrderReq createOrderReq) {
		log.info("Creating order in PaymentServiceImpl || createOrderReq: {}", createOrderReq);

		/*
		 * TODO 1. getAccessToken (OAuth) 2. Call PayPal createOrder 3.
		 * Success/Failure/TimeOut - Proper response handling 4. What to return to your
		 * calling service (payment-processing-service)
		 */
		
		String accessToken = tokenService.getAccessToken();
		log.info("Access token retrieved: {}", accessToken);

		HttpRequest httpRequest = createOrderHelper.prepareCreateOrderHttpRequest(createOrderReq, accessToken);
        log.info("Prepared HttpRequest for Create Order: {}", httpRequest);
		
		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("Create Order Response from PayPal: {}", response.getBody());

		PaypalOrder paypalOrder = jsonUtil.fromJson(response.getBody(), PaypalOrder.class);
		log.info("converted from JSON to PaypalOrder Object: {}", paypalOrder);

		CreateOrderRes createOrderRes = createOrderHelper.toCreateOrderRes(paypalOrder);
		log.info("CreateOrderRes to be returned: {}", createOrderRes);

		return createOrderRes;

	}

}
