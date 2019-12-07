package com.olima.ws.rest.model;

public class GenericUser {
	
	private String nombre;
	private Integer cedula;
	private Integer edad;
	
	public GenericUser(String nombre, Integer cedula, Integer edad) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.edad = edad;
	}
	
	public GenericUser() {
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
