package com.simtopup.user.boundary;

import java.io.Serializable;
import java.util.Map;
import com.serverlite.core.entity.Session;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.entity.User;

public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8192276824346820622L;
	
	private Integer eventId;
	private Session session;
	private Map<String, String> queryParams;
	private Kaptcha kaptcha;
	private User user;
	private TopUpOrder topUpOrder;
	private PaymentNotificaton paymentNotificaton;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Kaptcha getKaptcha() {
		return kaptcha;
	}
	public void setKaptcha(Kaptcha kaptcha) {
		this.kaptcha = kaptcha;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public TopUpOrder getTopUpOrder() {
		return topUpOrder;
	}
	public void setTopUpOrder(TopUpOrder topUpOrder) {
		this.topUpOrder = topUpOrder;
	}
	public PaymentNotificaton getPaymentNotificaton() {
		return paymentNotificaton;
	}
	public void setPaymentNotificaton(PaymentNotificaton paymentNotificaton) {
		this.paymentNotificaton = paymentNotificaton;
	}
	
	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	@Override
	public String toString() {
		return "Request [eventId=" + eventId + ", session=" + session + ", queryParams=" + queryParams + ", kaptcha="
				+ kaptcha + ", user=" + user + ", topUpOrder=" + topUpOrder + ", paymentNotificaton="
				+ paymentNotificaton + "]";
	}
	
}
