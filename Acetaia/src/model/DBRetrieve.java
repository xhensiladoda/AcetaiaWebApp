package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *  Classe che si occupa della costruzione delle query, a seconda del tipo di operazione, della loro esecuzione e
 *  del recupero dei dati dal DB
 */
public class DBRetrieve {

	private DBConnection db;
	private String op;
	private Data date;
	private int idOp;
	private DBList table[]; // memorizza i dati della tabella barile o storico del DB
	private int columns_tab; // numero di colonne della tabella barile o storico del DB
	private int rows_tab; // numero di record in barile o storico del DB
	private String ricerca;
	private DBList plusData[];
	private DBList plusDataNames[];
	
	/**
	 * inizializza le variabili globali 
	 * @param s stringa corrispondente all'operazione da eseguire
	 */
	public DBRetrieve(String s) {
		db = new DBConnection();
		op = s;
		date = new Data();
		idOp = db.getIdOp();
		ricerca=null;
	}
	
	/**
	 * costruisce le query a seconda dei parametri passati e le esegue nel database
	 * @param param array di Object che rappresentano tutti i valori dei parametri delle query
	 */
	public void setQuery (Object[] param) {
		//funzione di update
		// create new DB record in storico
		String myQuery="INSERT INTO `acetaia`.`storico` VALUES "
				+ "("+param[0]+","+param[1]+",'"+date.getDate()+"','"+op+"',"+idOp+");";
		db.executeInsUpDel(myQuery);
		if(op.contentEquals("rabbocco")) {
			myQuery="INSERT INTO `acetaia`.`storico` VALUES "
					+ "("+param[2]+","+param[3]+",'"+date.getDate()+"','"+op+"',"+idOp+");";
			db.executeInsUpDel(myQuery);
		}
		// create new DB record nella tabella dell'operazione
		myQuery = "INSERT INTO `acetaia`.`"+op+"` VALUES ("+idOp+","+param[0];
		int end = 0;
		switch(op) {
			case("misurazione"): 
			case("degustazione"): end = param.length; break;
			case("aggiunta"):
			case("prelievo"): end = param.length-1; break;
			case("rabbocco"): end = param.length-2;
		}
		boolean apici = op.contentEquals("aggiunta")||op.contentEquals("misurazione")||op.contentEquals("degustazione");
		for (int i=2; i<end; i++) { 
			myQuery = myQuery + ",";
			if((i==3)&&op.contentEquals("rabbocco")) i++; // vado a idBarile_dst
			if((i==2)&&apici) myQuery = myQuery + "'"; // campo: tipo/parametro/file
			myQuery = myQuery + param[i];
			if((i==2)&&apici) myQuery = myQuery + "'"; // campo: tipo/parametro/file
		}
		myQuery = myQuery + ");";
		db.executeInsUpDel(myQuery);
		// update del record in barile
		if(!op.contentEquals("degustazione")) {
			if(op.contentEquals("rabbocco")) {
				myQuery = "UPDATE `acetaia`.`barile` SET livello="+param[param.length-2]
						+" WHERE idbarile="+param[2]+";";
				db.executeInsUpDel(myQuery);
			}
			myQuery = "UPDATE `acetaia`.`barile` SET "+(op.contentEquals("misurazione")?param[2]:"livello")
					+"="+param[param.length-1]+" WHERE idbarile="+param[0]+";";
			db.executeInsUpDel(myQuery);
		}
		
	}
	
	/**
	 * funzione di visualizzazione che serve per passare i dati alla jsp nella funzione get
	 * @return oggetto che memorizza i dati delle tabelle barile o storico del DB
	 */
	public DBList[] show(){
		rows_tab = 0; //numero di righe
		ResultSet rs=null;
        String query=null;
        if(op.contentEquals("misurazione") || op.contentEquals("degustazione") || op.contentEquals("aggiunta")
        		|| op.contentEquals("prelievo") || op.contentEquals("rabbocco"))
        	query="SELECT * FROM barile";
        else query=ricerca;
        //
        rs=db.executeSelect(query);
        ResultSetMetaData rsmd;
        try {
        	 rsmd=rs.getMetaData();
        	 columns_tab=rsmd.getColumnCount();
        	 table=new DBList[columns_tab];
             for(int i=0; i<columns_tab; i++)
             	table[i]=new DBList();
			 while(rs.next()){
				 for(int i=1; i<=columns_tab; i++){
					 table[i-1].setData(rs.getObject(i).toString());
				 }
			  }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        rows_tab = db.getSize();
        try {
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        return table;
	}
		
	/**
	 * funzione per creare la query della ricerca
	 * @param param parametri con cui fare i confronti nella query
	 */
	public void createRicerca(Object[] param){
		String query = "SELECT * FROM `acetaia`.`storico` WHERE";
		boolean init = false;
		if(param[0]!=null) {
			query = query+" barile = "+param[0];
			init = true;
		}
		if(param[1]!=null) {
			query = query+(init?" AND ":" ")+"batteria = "+param[1];
			init = true;
		}
		if(param[2]!=null) {
			query = query+(init?" AND ":" ")+"data >= '"+param[2]+"'";
			init = true;
		}
		if(param[3]!=null) {
			query = query+(init?" AND ":" ")+"data <='"+param[3]+"'";
			init = true;
		}
		if(param[4]!=null) {
			query = query+(init?" AND ":" ")+"operazione='"+param[4]+"'";
			init = true;
		}
		ricerca=query;
	}
	
	/**
	 * riempie le liste contenenti i dati delle tabelle del DB delle operazioni
	 */
	public void setPlusData(){
		plusData=new DBList[rows_tab];
		plusDataNames=new DBList[rows_tab];
		for(int i=0; i<rows_tab; i++){
			plusData[i]=new DBList();
			plusDataNames[i]=new DBList();
		}
		ResultSet ro=null;
		ResultSet rb=null;
		for(int i=0; i<rows_tab; i++){
			ResultSetMetaData rsmd;
			int col_number=0;
			//informazioni aggiuntive per l'operazione
			ro=db.executeSelect("SELECT o.* FROM `acetaia`.`"+table[3].getData(i)+"` o "
				+ "WHERE o.idOperazione="+table[4].getData(i));
			try {
				rsmd = ro.getMetaData();
				col_number=rsmd.getColumnCount();
				while(ro.next()){
					//valori per l'operazione i-esima
					for(int j=1; j<=col_number; j++){
						plusDataNames[i].setData(rsmd.getColumnName(j)); //ottengo i nomi delle colonne dell'operazione
						plusData[i].setData(ro.getObject(j).toString()); //ottengo i dati aggiuntivi per l'operazione
					}
				}
				ro.close();
			} catch (SQLException e1) {e1.printStackTrace();}
			
			//informazioni aggiuntive per il barile
			rb=db.executeSelect("SELECT o.capacita, o.maxLivello,o.livello,o.legno,"
					+ "o.acidita, o.densita FROM `acetaia`.`barile` o "
					+ "WHERE o.idBarile="+table[0].getData(i));
			try {
				rsmd = rb.getMetaData();
				col_number=rsmd.getColumnCount();
				while(rb.next()){
					//valori per l'operazione i-esima
					for(int j=1; j<=col_number; j++){
						plusDataNames[i].setData(rsmd.getColumnName(j)); //ottengo i nomi delle colonne dell'operazione
						plusData[i].setData(rb.getObject(j).toString()); //ottengo i dati aggiuntivi per l'operazione
					}
				}
				rb.close();
			} catch (SQLException e1) {e1.printStackTrace();}
		}
	}
	
	/**
	 * @return oggetto che memorizza i dati delle tabelle del DB delle operazioni
	 */
	public DBList[] getPlusData(){
		return plusData;
	}
	
	/**
	 * @return oggetto che memorizza i nome delle colonne delle tabelle del DB delle operazioni
	 */
	public DBList[] getPlusDataNames(){
		return plusDataNames;
	}
	
	/**
	 * @return il numero di record in barile o storico del DB
	 */
	public int getRows() {
    	return rows_tab;
    }
	
	/**
	 * @return il numero di colonne della tabella barile o storico del DB
	 */
	public int getCols() {
    	return columns_tab;
    }
    
}
