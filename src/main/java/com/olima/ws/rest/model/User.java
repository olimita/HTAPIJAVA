package com.olima.ws.rest.model;

public class User {
	
	private Integer id;
	private String nombre;
	private Integer cedula;
	private Integer edad;
	
	public User(Integer id, String nombre, Integer cedula, Integer edad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cedula = cedula;
		this.edad = edad;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCedula() {
		return cedula;
	}
	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	

}
