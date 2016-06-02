package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * recupera dal DB username e password autorizzati
 */
public class DBUser {
	
	private String username;
	private String password;
	private DBConnection db;
	
	/**
	 * apre la connessione col database
	 * @param username username per il quale deve essere validata la password
	 */
	public DBUser(String username){
		this.username=username;
		this.password="";
		db = new DBConnection();
	}
	
	/**
	 * controlla se username è giusto; in caso positivo restituisce la password
	 * @return la password se il nome utente è valido; stringa vuota altrimenti
	 */
	public String getPassword(){
		ResultSet rs=null;
		rs = db.executeSelect("SELECT * FROM admin");
		try {
			while(rs.next()){
				if(this.username.contentEquals(rs.getObject("username").toString())){
					password=rs.getObject("password").toString();
					break;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}

}
