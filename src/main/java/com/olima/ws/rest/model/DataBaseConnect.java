package com.olima.ws.rest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
	
	private Connection conexion;
	
	private String url = "jdbc:postgresql://[ec2-50-19-95-77.compute-1.amazonaws.com]:5432/d2unleamt9c3kh";
	
	public void connectDB() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection(url, "kwcllsjyifqjvn", "8f7e5cbd461da9b9e0ade6ccc551dc06b2f75c17a5a1d921ef79d33cd7ef18a7");
			System.out.println("Conectadito.");
		} catch (ClassNotFoundException ex) {
			// log an exception. for example:
			System.out.println("Driver not found.");
		}
	}
	
	public void disconnectDB() {
		try {
			if (conexion != null) {
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("disconnect failed");
		}
	}

	public Connection getCon() {
		return conexion;
	}

	public void setCon(Connection con) {
		this.conexion = con;
	}

}
