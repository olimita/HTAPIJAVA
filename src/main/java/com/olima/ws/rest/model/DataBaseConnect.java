package com.olima.ws.rest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
	
	private Connection conexion;
	
	private String url = "jdbc:postgresql:ec2-174-129-254-216.compute-1.amazonaws.com:5432/de05vd2d92u17r";
	
	public void connectDB() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://[ec2-174-129-254-216.compute-1.amazonaws.com]:5432/de05vd2d92u17r", "rtnpyhcfabctic", "1e509d63689ae17ba84b736e8ce0eed58ed92a2ae20e7563ff58f380add78ee6");
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
