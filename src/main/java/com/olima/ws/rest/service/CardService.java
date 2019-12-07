package com.olima.ws.rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.olima.ws.rest.model.CCard;
import com.olima.ws.rest.model.DataBaseConnect;
import com.olima.ws.rest.model.GenericCCard;


public class CardService {
	
	private DataBaseConnect con = new DataBaseConnect();
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	public CardService() {
		try {
			con.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<CCard> getAllCards() throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from creditcards)");
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		List<CCard> list = new ArrayList<CCard>();
		statement = con.getCon().createStatement();
		resultSet = statement.executeQuery("select * from creditcards");
		while (resultSet.next()) {
			Integer ccard_id = resultSet.getInt("ccard_id");
			Integer user_id = resultSet.getInt("user_id");
			Integer saldo = resultSet.getInt("saldo");
			list.add(new CCard(ccard_id, user_id, saldo));
		}

		return list;
	}
	
	public CCard getCardById(Integer ccard_ids) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from creditcards where ccard_id=?)");
		preparedStatement.setInt(1, ccard_ids);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon().prepareStatement("select * from creditcards where ccard_id=?");
		preparedStatement.setInt(1, ccard_ids);
		resultSet = preparedStatement.executeQuery();
		CCard card = null;
		while (resultSet.next()) {
			Integer ccard_id = resultSet.getInt("ccard_id");
			Integer user_id = resultSet.getInt("user_id");
			Integer saldo = resultSet.getInt("saldo");
			card = new CCard(ccard_id, user_id, saldo);
		}
		return card;

	}

	public GenericCCard createCard(GenericCCard card) throws SQLException {
		preparedStatement = con.getCon().prepareStatement("INSERT INTO creditcards (user_id, saldo) VALUES (?, ?)");
		preparedStatement.setInt(1, card.getUser_id());
		preparedStatement.setInt(2, card.getSaldo());
		preparedStatement.executeUpdate();
		return card;
	}

	public GenericCCard updateCard(Integer card_id, GenericCCard card) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from creditcards where ccard_id=?)");
		preparedStatement.setInt(1, card_id);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon()
				.prepareStatement("UPDATE creditcards SET user_id = ?, saldo = ? WHERE ccard_id = ?");
		preparedStatement.setInt(1, card.getUser_id());
		preparedStatement.setInt(2, card.getSaldo());
		preparedStatement.setInt(3, card_id);
		preparedStatement.executeUpdate();
		return card;
	}

	public Integer deleteCard(Integer card_id) throws SQLException {
		
		preparedStatement = con.getCon()
				.prepareStatement("select exists(select 1 from creditcards where ccard_id=?)");
		preparedStatement.setInt(1, card_id);
		resultSet = preparedStatement.executeQuery();
		
		String exists = "t";
		
		while (resultSet.next()) {
			exists = resultSet.getString("exists");
		}
		
		if(exists.equals("f")) {
			return null;
		}
		
		preparedStatement = con.getCon().prepareStatement("delete from creditcards where ccard_id=?");
		preparedStatement.setInt(1, card_id);
		preparedStatement.executeUpdate();
		return card_id;
	}
	
	public DataBaseConnect getDbCon() {
		return con;
	}

	public void setCon(DataBaseConnect con) {
		this.con = con;
	}

}
