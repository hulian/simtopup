package com.simtopup.user.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TopUpOrder {
	
	public static final String NETWORK_OPERATOR_THAI_DTAC="THAI_DTAC";
	public static final String NETWORK_OPERATOR_THAI_TRUE="THAI_TRUE";
	public static final String NETWORK_OPERATOR_THAI_AIS="THAI_AIS";
	
	public static final int COUNTRY_CODE_THAI=66;
	
	public static final int UNPAID = 10;
	public static final int PAID = 20;
	public static final int TOPUPED = 30;
	
	//ID
	private Integer id;

	private String topUpOrderId;
	
	private String topUpTransactionId;
	
	//付款方式
	private String payMethod;

	private String paymentOrderId=UUID.randomUUID().toString();
	
	//付款事物id
	private String paymentTransactionId;
	
	//国家代码
	private Integer countryCode;

	//网络运营商
	private String networkOperator;
	
	//手机号
	private String mobileNumber;
	
	//充值金额
	private BigDecimal topUpAmount;
	
	//实际支付金额
	private BigDecimal paymentAmount;
	
	//币种
	private String currency="CNY";
	
	//汇率
	private BigDecimal exchangeRate=new BigDecimal(1);
	
	//
	private Integer status=UNPAID;
	
	//创建时间
	private Date createTime=new Date();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public String getNetworkOperator() {
		return networkOperator;
	}

	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public BigDecimal getTopUpAmount() {
		return topUpAmount;
	}

	public void setTopUpAmount(BigDecimal topUpAmount) {
		this.topUpAmount = topUpAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public String getPaymentOrderId() {
		return paymentOrderId;
	}

	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTopUpTransactionId() {
		return topUpTransactionId;
	}

	public void setTopUpTransactionId(String topUpTransactionId) {
		this.topUpTransactionId = topUpTransactionId;
	}

	public String getTopUpOrderId() {
		return topUpOrderId;
	}

	public void setTopUpOrderId(String topUpOrderId) {
		this.topUpOrderId = topUpOrderId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public String toString() {
		return "TopUpOrder [id=" + id + ", topUpOrderId=" + topUpOrderId + ", topUpTransactionId=" + topUpTransactionId
				+ ", payMethod=" + payMethod + ", paymentOrderId=" + paymentOrderId + ", paymentTransactionId="
				+ paymentTransactionId + ", countryCode=" + countryCode + ", networkOperator=" + networkOperator
				+ ", mobileNumber=" + mobileNumber + ", topUpAmount=" + topUpAmount + ", paymentAmount=" + paymentAmount
				+ ", currency=" + currency + ", exchangeRate=" + exchangeRate + ", status=" + status + ", createTime="
				+ createTime + "]";
	}
	
	

}
