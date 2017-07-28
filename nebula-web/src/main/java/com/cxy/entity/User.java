package com.cxy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(name="sys_user")
public class User extends IdEntity{

    @Column(name="username")
	private String username;
    
    @Column(name="password")
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
