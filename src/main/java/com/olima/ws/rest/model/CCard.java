package com.olima.ws.rest.model;

public class CCard {
	
	private Integer ccard_id;
	private Integer user_id;
	private Integer saldo;
	
	public CCard(Integer ccard_id, Integer user_id, Integer saldo) {
		super();
		this.ccard_id = ccard_id;
		this.user_id = user_id;
		this.saldo = saldo;
	}
	
	public Integer getCcard_id() {
		return ccard_id;
	}
	public void setCcard_id(Integer ccard_id) {
		this.ccard_id = ccard_id;
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
