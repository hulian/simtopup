package com.simtopup.user.service.bean;

/**
 * 前端提交第三方支付需要的信息
 * @author User
 *
 */
public class PaymentSubmitBean {

	
	//第三方验证需要的加密字符串
	private String apiAddress;
	private String merchant;
	private String sign;
	private String orderId;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "PaymentSubmitBean [merchant=" + merchant + ", sign=" + sign + ", orderId=" + orderId + "]";
	}

	public String getApiAddress() {
		return apiAddress;
	}

	public void setApiAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}
	
}
