package com.simtopup.user.entity;

import java.io.Serializable;
import java.util.Date;


public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328509210288164373L;
	private Integer partitionId;
	private Integer userId;
	private String userName;
	private String password;
	private String roles;
	private Date createTime = new Date();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password
				+ ", roles=" + roles + ", createTime=" + createTime + "]";
	}
	public Integer getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(Integer partitionId) {
		this.partitionId = partitionId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
