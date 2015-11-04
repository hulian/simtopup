package com.simtopup.user.boundary;

public class PaymentNotificaton {
	
	private String payMethod;
	private String paymentTransactionId;
	private String paymentStatus;
	private String paymentOrderId;
	
	private String responseStatus;
	
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}
	public void setPaymentTransactionId(String paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentOrderId() {
		return paymentOrderId;
	}
	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	@Override
	public String toString() {
		return "PaymentNotificaton [payMethod=" + payMethod + ", paymentTransactionId=" + paymentTransactionId
				+ ", paymentStatus=" + paymentStatus + ", paymentOrderId=" + paymentOrderId + ", responseStatus="
				+ responseStatus + "]";
	}
	
}
