package com.simtopup.user.service.bean;

import java.math.BigDecimal;

public class PaymentOrderBean {
	
	private String paymentOrderId;
	private String paymentTransactionId;
	private BigDecimal paymentAmount;
	private String info;
	public String getPaymentOrderId() {
		return paymentOrderId;
	}
	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}
	public void setPaymentTransactionId(String paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}
	
	
}
