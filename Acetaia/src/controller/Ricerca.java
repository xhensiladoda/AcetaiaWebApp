package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet che gestisce l'operazione di Ricerca
 */
@WebServlet("/Ricerca")
public class Ricerca extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int barile=0;
	private int batteria=0;
	private Object[] param;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ricerca()throws SQLException{
        super();
    }
    
	/**
	 * prende i dati inseriti dall'utente nella jsp, li invia alla classe che si occupa di fare le query
	 * al DB e re-indirizza l'utente verso una pagina in cui viene visualizzato l'output della ricerca 
	 * se i dati erano corretti, a una di errore in caso contrario oppure se non esistono risultati
	 * conformi ai parametri inseriti
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String idbarile=null;
		String idbatteria=null;
		String periodo_i=null;
		String periodo_f=null;
		String intervento=null;
		param=new Object[5];
		//
		idbarile=request.getParameter("barile");
		idbatteria=request.getParameter("batteria");
		periodo_i=request.getParameter("periodo_i");
		periodo_f=request.getParameter("periodo_f");
		intervento=request.getParameter("intervento");
		HttpSession session = request.getSession(true);
		String error=check(idbarile, idbatteria, periodo_i, periodo_f,intervento);		
		if(error!=""){
			//mando la pagina di errore
			session.setAttribute("s","Attenzione! "+error);
			response.sendRedirect("Errore.jsp");
		} else{
			DBRetrieve dbq=new DBRetrieve("ricerca");
			dbq.createRicerca(param);
			DBList storico[]=dbq.show();
			dbq.setPlusData();
			DBList other_data[]=dbq.getPlusData();
			DBList col_names[]=dbq.getPlusDataNames();
			System.out.println(dbq.getRows()-1);
			System.out.println(dbq.getCols()-2);
			if(dbq.getRows()!=0){
				session.setAttribute("rows",dbq.getRows()-1); //passo il numero di righe -1 (siccome parte da 0 la JSP)
				session.setAttribute("columns",dbq.getCols()-2); //passo il numero di colonne -2 (siccome parte da 0 e non voglio visualizzare l'idOpla JSP)
				session.setAttribute("storico",storico); ////passo il numero di righe -1 (siccome parte da 0 la JSP)
				session.setAttribute("other_data",other_data); //tabella dei dati aggiuntivi
				session.setAttribute("col_names",col_names); //mi serve per i nomi dei dati aggiuntivi
				response.sendRedirect("Ricerca.jsp");
	   	 	}else {
				session.setAttribute("s","Non ci sono dati che corrispondono ai parametri inseriti!");
				response.sendRedirect("Errore.jsp");
	   	 	}//fine if(rows!=0)
		}		
	}
	
	/**
	 * verifica la presenza di errori nei parametri passati
	 * @param idbarile identificatore del barile
	 * @param idbatteria identificatore della batteria
	 * @param periodo_i data di inizio
	 * @param periodo_f data di fine
	 * @param intervento tipo di operazione eseguita
	 * @return una stringa contenente i possibili errori; se vuota non ci sono errori
	 */
	private String check(String idbarile, String idbatteria, String periodo_i, String periodo_f,String intervento){
		String errore_vuoto=null;
		String errore_periodo_i=null;
		String errore_periodo_f=null;
		String errore_barile=null;
		String errore_batteria=null;
		String errore_batteria_neg=null;
		String errore_barile_neg=null;
		String s="";
		//
		if((idbarile==null || idbarile.contentEquals("")) 
				&& (idbatteria==null || idbatteria.contentEquals(""))
				&& (periodo_i==null || periodo_i.contentEquals(""))
				&& (intervento==null || intervento.contentEquals("vuoto"))
				&& (periodo_f==null || periodo_f.contentEquals(""))){
			errore_vuoto="Compilare almeno uno dei cinque campi!";
		}
		
		if(idbarile!=null && !idbarile.toString().isEmpty()){
			boolean ok=false;
			try {
				barile = Integer.parseInt(idbarile);
				if(barile<=0) errore_barile_neg="Il valore del barile deve essere un intero positivo.";
				else ok=true;
			} catch (NumberFormatException e) {
				errore_barile="Il valore del barile deve essere un intero.";
			}
			if(ok==true) param[0]=barile;
		}
		if(!idbatteria.toString().isEmpty() && idbatteria!=null){
			boolean ok=false;
			try {
				batteria=Integer.parseInt(idbatteria);
				if(batteria<=0) errore_batteria_neg="Il valore della batteria deve essere un intero positivo.";
				else ok=true;
			} catch (NumberFormatException e) {
				errore_batteria="Il valore della batteria deve essere un intero.";
			}
			if(ok==true) param[1]=batteria;
		}	
		if(!periodo_i.toString().isEmpty() && periodo_i!=null){
			//controllo se il periodo_i_i inserito è nella forma corretta
			boolean ok=false;
			if(!periodo_i.matches("(.*)/(.*)/(.*)")){
				errore_periodo_i="L'INIZIO PERIODO inserito deve essere in formato AAAA/MM/GG.";
			} else {
				// controlla se nel formato sono stati inseriti lettere al posto di numeri
				int d=0;
				int m=0;
				int a=0;
				String temp=null;
				try { 
					temp=periodo_i.substring(0,4);
					a=Integer.parseInt(temp);
					temp=periodo_i.substring(5,7);
					m=Integer.parseInt(temp);
					temp=periodo_i.substring(8);
					d=Integer.parseInt(temp);
					ok=true;
				} catch (Exception e) {
					errore_periodo_i="Inserire nell'INIZIO PERIODO solo valori numerici nel formato AAAA/MM/GG.";
				}
			}
			if(ok==true) param[2]=periodo_i;
		}
		if(!periodo_f.toString().isEmpty() && periodo_f!=null){
			//controllo se il periodo_i_i inserito è nella forma corretta
			boolean ok=false;
			if(!periodo_f.matches("(.*)/(.*)/(.*)")){
				errore_periodo_f="Il FINE PERIODO inserito deve essere in formato aaaa/mm/gg.";
			} else {
				// controlla se nel formato sono stati inseriti lettere al posto di numeri
				int d=0;
				int m=0;
				int a=0;
				String temp=null;
				try {
					temp=periodo_f.substring(0,4);
					temp=periodo_f.substring(5,7);
					m=Integer.parseInt(temp);
					temp=periodo_f.substring(8);
					d=Integer.parseInt(temp);
					ok=true;
				} catch (Exception e) {
					errore_periodo_f="Inserire nel FINE PERIODO solo valori numerici.";
				}
			}
			if(ok==true) param[3]=periodo_f;
		}
		if(!intervento.toString().isEmpty() && !intervento.contentEquals("vuoto"))
			param[4]=intervento;
		//
		if(errore_vuoto!=null) s = s +"<br>"+ errore_vuoto;
		if(errore_barile!=null) s = s +"<br>"+ errore_barile;
		if(errore_batteria!=null) s = s +"<br>"+ errore_batteria;
		if(errore_periodo_i!=null) s = s +"<br>"+ errore_periodo_i;
		if(errore_periodo_f!=null) s = s +"<br>"+ errore_periodo_f;
		if(errore_barile_neg!=null) s = s +"<br>"+ errore_barile_neg;
		if(errore_batteria_neg!=null) s = s +"<br>"+ errore_batteria_neg;
		return s;
	}

}
