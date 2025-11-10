package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.pojo.CreateOrderReq;
import com.hulkhiretech.payments.pojo.CreateOrderRes;

public interface PaymentService {
	
	public CreateOrderRes createOrder(CreateOrderReq createOrderReq);

}
