package com.olima.ws.rest.model;

public class GenericCCard {
	
	private Integer user_id;
	private Integer saldo;
	
	public GenericCCard(Integer user_id, Integer saldo) {
		super();
		this.user_id = user_id;
		this.saldo = saldo;
	}
	
	public GenericCCard() {
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}
	

}
