package com.olima.ws.rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.olima.ws.rest.model.User;

import java.util.ArrayList;
import java.util.List;

import com.olima.ws.rest.model.CCard;
import com.olima.ws.rest.model.DataBaseConnect;
import com.olima.ws.rest.model.GenericUser;

public class UserService {
	
	private DataBaseConnect con = new DataBaseConnect();
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	public UserService() {
		try {
			con.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<User> getAllUsers() throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from users)");
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		List<User> list = new ArrayList<User>();
		statement = con.getCon().createStatement();
		resultSet = statement.executeQuery("select * from users");
		while (resultSet.next()) {
			Integer id = resultSet.getInt("id");
			String nombre = resultSet.getString("nombre");
			Integer cedula = resultSet.getInt("cedula");
			Integer edad = resultSet.getInt("edad");
			list.add(new User(id, nombre, cedula, edad));
		}

		return list;
	}
	
	public User getUserById(Integer ids) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from users where id=?)");
		preparedStatement.setInt(1, ids);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon().prepareStatement("select * from users where id=?");
		preparedStatement.setInt(1, ids);
		resultSet = preparedStatement.executeQuery();
		User user = null;
		
		while (resultSet.next()) {
			Integer id = resultSet.getInt("id");
			String nombre = resultSet.getString("nombre");
			Integer cedula = resultSet.getInt("cedula");
			Integer edad = resultSet.getInt("edad");
			user = new User(id, nombre, cedula, edad);
		}
		return user;

	}

	public GenericUser createUser(GenericUser user) throws SQLException {
		preparedStatement = con.getCon().prepareStatement("insert into users (nombre, cedula, edad) values ( ?, ?, ?)");
		preparedStatement.setString(1, user.getNombre());
		preparedStatement.setInt(2, user.getCedula());
		preparedStatement.setInt(3, user.getEdad());
		preparedStatement.executeUpdate();
		return user;
	}

	public GenericUser updateUser(Integer id, GenericUser user) throws SQLException {
		
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from users where id=?)");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		
		preparedStatement = con.getCon()
				.prepareStatement("UPDATE users SET nombre = ?, cedula = ?, edad = ? WHERE id = ?");
		preparedStatement.setString(1, user.getNombre());
		preparedStatement.setInt(2, user.getCedula());
		preparedStatement.setInt(3, user.getEdad());
		preparedStatement.setLong(4, id);
		preparedStatement.executeUpdate();
		return user;
	}

	public Integer deleteUser(Integer id) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from users where id=?)");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon().prepareStatement("delete from users where id=?");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		return id;
	}
	
	public List<CCard> getUserCards(Integer user_ids) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from creditcards where user_id=?)");
		preparedStatement.setInt(1, user_ids);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon().prepareStatement("select * from creditcards where user_id=?");
		preparedStatement.setInt(1, user_ids);
		resultSet = preparedStatement.executeQuery();
		List<CCard> list = new ArrayList<CCard>();
		while (resultSet.next()) {
			Integer ccard_id = resultSet.getInt("ccard_id");
			Integer user_id = resultSet.getInt("user_id");
			Integer saldo = resultSet.getInt("saldo");
			list.add(new CCard(ccard_id, user_id, saldo));
		}

		return list;

	}
	
	public DataBaseConnect getDbCon() {
		return con;
	}

	public void setCon(DataBaseConnect con) {
		this.con = con;
	}

}
