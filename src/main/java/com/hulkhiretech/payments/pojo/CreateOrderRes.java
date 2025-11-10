package com.hulkhiretech.payments.pojo;

import lombok.Data;

@Data
public class CreateOrderRes {
private String orderId;
private String paypalStatus;
private String redirectUrl;

}
