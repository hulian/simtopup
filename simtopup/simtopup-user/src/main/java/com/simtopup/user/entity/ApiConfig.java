package com.simtopup.user.entity;

public class ApiConfig {
	
	public static final int THAI_TOP_UP_API_ID = 1;

	private Integer id;
	
	private String name;
	
	private String key1;
	
	private String key2;
	
	private String key3;
	
	private String apiAddress;
	
	private String merchant;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getApiAddress() {
		return apiAddress;
	}

	public void setApiAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	

}
