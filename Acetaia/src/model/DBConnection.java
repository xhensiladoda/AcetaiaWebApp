package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe che si occupa della gestione della connessione al database
 */
public class DBConnection {
	
	private Connection con;
	private Statement stmt;
	private int size;
	
	/**
	 * inizializza le variabili globali e apre la connessione col DB
	 */
	public DBConnection() {
		con = null;  
		stmt = null;
		size=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/acetaia","root","root");
			stmt = con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * esegue la query di select specificata e conta il numero di righe selezionate
	 * @param query la query che deve essere eseguita
	 * @return il result set prodotto dalla select
	 */
	public ResultSet executeSelect(String query) {
		ResultSet r = null;
		int cont = 0;
		try {
			r = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(r.first()){
				cont++;
				while(r.next()){
					cont++; // conta i record
				}
			}
			r.beforeFirst(); //per ritornare di nuovo all'inizio, altrimenti ritorna un rs vuoto
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		size = cont;
		return  r; 
	}
	
	/**
	 * @return il numero di righe interessate dalla query di select eseguita in executeSelect
	 */
	public int getSize(){		
		return size;
	}
		
	/**
	 * esegue una query di insert, delete o update
	 * @param query la query che deve essere eseguita
	 */
	public void executeInsUpDel(String query) {
		try {
			stmt.executeUpdate(query); 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		  }
	}
	
	/**
	 * rilascia le risorse del DB
	 * @throws SQLException eccezione che fornisce informazioni su errori avvenuti nelle interazioni col DB
	 */
	public void closeConnection() throws SQLException{
		stmt.close();
	}
	
	/**
	 * recupera l'identificatore dell'ultima operazione eseguita e lo incrementa
	 * @return identificatore dell'operazione corrente
	 */
	public int getIdOp() {
		ResultSet r = null;
		int id=0;
		String s = null;
		String query="SELECT idOp FROM acetaia.storico;";
		r = executeSelect(query);
		try {
			// Moves the cursor to the last row in r and gets id of last operation
			if(r.last()){
				s = r.getObject(1).toString();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		if(s==null) return 1;
		id = Integer.parseInt(s);
		return id+1;
	}
	
	
	
}


