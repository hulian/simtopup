package com.simtopup.user.service.impl.thaitopup;

public class ThaiTopUpResponse {

	private String uid;
	
	private String message;
	
	private String amount;
	
	private String order_id;
	
	private String phone_number;
	
	private String network;
	
	private String status;
	
	private ThaiTopUpError error;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ThaiTopUpError getError() {
		return error;
	}

	public void setError(ThaiTopUpError error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ThaiTopUpResponse [uid=" + uid + ", message=" + message + ", amount=" + amount + ", order_id="
				+ order_id + ", phone_number=" + phone_number + ", network=" + network + ", status=" + status + "]";
	}
	
}
