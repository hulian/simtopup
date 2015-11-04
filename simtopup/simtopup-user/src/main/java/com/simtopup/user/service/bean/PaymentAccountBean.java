package com.simtopup.user.service.bean;

public class PaymentAccountBean {

	private String apiAddress;
	
	private String merchantId;
	
	private String merchantUserName;
	
	private String merchantKey;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getApiAddress() {
		return apiAddress;
	}

	public void setApiAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}
	
}
