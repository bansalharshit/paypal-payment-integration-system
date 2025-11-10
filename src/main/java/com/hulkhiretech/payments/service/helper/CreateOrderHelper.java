package com.hulkhiretech.payments.service.helper;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.constant.Constant;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.paypal.req.Amount;
import com.hulkhiretech.payments.paypal.req.ExperienceContext;
import com.hulkhiretech.payments.paypal.req.OrderRequest;
import com.hulkhiretech.payments.paypal.req.PaymentSource;
import com.hulkhiretech.payments.paypal.req.Paypal;
import com.hulkhiretech.payments.paypal.req.PurchaseUnit;
import com.hulkhiretech.payments.paypal.res.PaypalOrder;
import com.hulkhiretech.payments.pojo.CreateOrderReq;
import com.hulkhiretech.payments.pojo.CreateOrderRes;
import com.hulkhiretech.payments.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateOrderHelper {
	
	@Value("${paypal.create-order-url}")
	private String CreateOrderUrl;
	
	private final JsonUtil jsonUtil;
	
	public HttpRequest prepareCreateOrderHttpRequest(CreateOrderReq createOrderReq, String accessToken) {
		
		log.info("Preparing HttpRequest for Create Order || createOrderReq: {}, accessToken: {}", createOrderReq, accessToken);
		HttpHeaders headers = prepareRequestHeader(accessToken);

		String requestAsJson = prepareReqBodyAsJson(createOrderReq);

//		// Make HTTP Request
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(CreateOrderUrl);
		httpRequest.setHttpHeaders(headers);
		httpRequest.setBody(requestAsJson);
		
		log.info("Prepared HttpRequest for Create Order: {}", httpRequest);
		
		return httpRequest;
	}

	private String prepareReqBodyAsJson(CreateOrderReq createOrderReq) {
		Amount amount = new Amount();
		amount.setCurrencyCode(createOrderReq.getCurrencyCode());

		String formattedAmount = String.format(Constant.TWO_DECIMAL_FACTOR, createOrderReq.getAmount());
		amount.setValue(formattedAmount);

		PurchaseUnit purchaseUnitRequest = new PurchaseUnit();
		purchaseUnitRequest.setAmount(amount);

		ExperienceContext experienceContext = new ExperienceContext();
		experienceContext.setPaymentMethodPreference(Constant.IMMEDIATE_PAYMENT_REQUIRED);
		experienceContext.setLandingPage(Constant.LANDINGPAGE_LOGIN);
		experienceContext.setShippingPreference(Constant.SHIPPING_PREF_NO_SHIPPING);
		experienceContext.setUserAction(Constant.USER_ACTION_PAY_NOW);
		experienceContext.setReturnUrl(createOrderReq.getReturnUrl());
		experienceContext.setCancelUrl(createOrderReq.getCancelUrl());

		Paypal paypal = new Paypal();
		paypal.setExperienceContext(experienceContext);

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setPaypal(paypal);

		// Final order request
		OrderRequest OrderReq = new OrderRequest();
		OrderReq.setIntent(Constant.INTENT_CAPTURE);
		OrderReq.setPurchaseUnits(Collections.singletonList(purchaseUnitRequest));
		OrderReq.setPaymentSource(paymentSource);

		// Convert to JSON
		String requestAsJson = jsonUtil.toJson(OrderReq);
		log.info("Create Order Request JSON: {}", requestAsJson);
		return requestAsJson;
	}

	private HttpHeaders prepareRequestHeader(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set headers PayPal-Request-Id => UUID
		String uuid = java.util.UUID.randomUUID().toString();
		log.info("Generated UUID for PayPal-Request-Id: {}", uuid);
		headers.add(Constant.PAY_PAL_REQUEST_ID, uuid);
		return headers;
	}

	public CreateOrderRes toCreateOrderRes(PaypalOrder paypalOrder) {
		log.info("Converting PaypalOrder to CreateOrderRes || paypalOrder: {}", paypalOrder);
		CreateOrderRes createOrderRes = new CreateOrderRes();

		createOrderRes.setOrderId(paypalOrder.getId());
		createOrderRes.setPaypalStatus(paypalOrder.getStatus());

		String redirectUrlLink = paypalOrder.getLinks().stream()
				.filter(link -> "payer-action".equalsIgnoreCase(link.getRel())).findFirst()
				.map(PaypalLink -> PaypalLink.getHref()).orElse(null);

		createOrderRes.setRedirectUrl(redirectUrlLink);

		log.info("Converted PaypalOrder to CreateOrderRes: {}", createOrderRes);
		return createOrderRes;
	}
	
}
