package com.simtopup.user.boundary;

import java.io.Serializable;

import com.serverlite.core.constant.Code;
import com.simtopup.user.service.bean.PaymentSubmitBean;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130003808483092403L;
	
	private String token;
	private int code=Code.SUCCESS;
	private String message="SUCCESS";
	private Kaptcha kaptcha;
	private PaymentSubmitBean paymentSubmitBean;
	
	public Kaptcha getKaptcha() {
		return kaptcha;
	}
	public void setKaptcha(Kaptcha kaptcha) {
		this.kaptcha = kaptcha;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PaymentSubmitBean getPaymentSubmitBean() {
		return paymentSubmitBean;
	}
	public void setPaymentSubmitBean(PaymentSubmitBean paymentSubmitBean) {
		this.paymentSubmitBean = paymentSubmitBean;
	}
	
}
