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
 * Servlet che gestisce l'operazione di Rabbocco
 */
@WebServlet("/Rabbocco")
public class Rabbocco extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBList barile[];
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rabbocco() throws SQLException {
        super();
    }
    
    /**
     * recupera le informazioni presenti nella tabella del DB barile e le invia alla jsp per la visualizzazione
     * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DBRetrieve b=new DBRetrieve("rabbocco");
		barile=b.show();
		int row = b.getRows();
		int col=b.getCols(); 
		HttpSession session = request.getSession(true);
		session.setAttribute("r",row-1); //righe
		session.setAttribute("c",col-3); //colonne (non saranno visualizzate le ultime due per semplicità di visualizzazione)
   	 	session.setAttribute("o",barile);
		response.sendRedirect("Rabbocco.jsp");
	}

	/**
	 * prende i dati inseriti dall'utente nella jsp, li invia alla classe che si occupa di fare le query
	 * al DB e re-indirizza l'utente verso una pagina di successo se  i dati erano corretti, a una di 
	 * errore in caso contrario
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String src;
		String dst;
		String quantita;
		// retrieves params from jsp
		src=request.getParameter("src");
		dst=request.getParameter("dst");
		quantita=request.getParameter("quantita");
		String error = "";
		error = check(src, dst, quantita);
		HttpSession session = request.getSession(true);
		if(error!=""){
			session.setAttribute("s",error);
			response.sendRedirect("Errore.jsp");
		} else {
			DBRetrieve dbq = new DBRetrieve("rabbocco");
			int bar_s = 0;
			int bar_d = 0;
			int lev_s = 0;
			int lev_d = 0;
			int q = 0;
			bar_s = Integer.parseInt(src);
			bar_s-=1; // devo accedere a una lista e usare bar come indice
			lev_s = Integer.parseInt(barile[3].getData(bar_s));//nella quarta colonna c'è il livello
			bar_d = Integer.parseInt(dst);
			bar_d-=1; // devo accedere a una lista e usare bar come indice
			lev_d = Integer.parseInt(barile[3].getData(bar_d));//nella quarta colonna c'è il livello
			q = Integer.parseInt(quantita);
			int batt_s = Integer.parseInt(barile[5].getData(bar_s));//nella sesta colonna c'è la batteria
			int batt_d = Integer.parseInt(barile[5].getData(bar_d));//nella sesta colonna c'è la batteria
			int newLev_s = lev_s-q;
			int newLev_d = lev_d+q;
			Object[] param = {src, batt_s, dst, batt_d, quantita, newLev_d, newLev_s}; //inverto s e d nei livelli perchè mi serve così in DBQuery
			dbq.setQuery(param);
			//
			response.sendRedirect("Successo.jsp");
		}
	}
	
	/**
	 * verifica la presenza di errori nei parametri passati
	 * @param src identificatore del barile dal quale viene fatto il prelievo
	 * @param dst identificatore del barile in cui viene fatta l'aggiunta
	 * @param quantita quantità di prodotto aggiunto
	 * @return una stringa contenente i possibili errori; se vuota non ci sono errori
	 */
	private String check(String src, String dst, String quantita) {
		String s = "";
		String error_vuoto = null;
		String error_q = null;
		String error_src = null;
		String error_dst = null;
		String error_sel = null;
		int q = 0;
		int max = 0;
		int lev_s = 0;
		int lev_d = 0;
		int bar_s = 0;
		int bar_d = 0;
		// check: ogni campo è compilato
		if(src==null||dst==null||quantita=="") {
			error_vuoto = "Attenzione, è necessario compilare tutti i campi.";
		}
		// check: il barile da cui si preleva e quello in cui si versa devono essere diversi
		if(src!=null && dst!=null){
			if (src.contentEquals(dst)) {
				error_sel = "I barili scelti devono essere diversi.";
			}			
		}
		if(quantita!="") {
			// check: quantità deve essere un numero intero
			try {
				q = Integer.parseInt(quantita);
				if(q<=0) error_q = "La quantità deve essere un valore intero positivo.";
			} catch (NumberFormatException e) {
				error_q = "Il valore inserito deve essere un intero.";
			}
			if(q!=0 && src!=null){
				// check: la quantità prelevata non deve superare quella contenuta nel barile
				bar_s = Integer.parseInt(src);
				bar_s-=1; // devo accedere a una lista e usare bar come indice
				lev_s = Integer.parseInt(barile[3].getData(bar_s));//nella quarta colonna c'è il livello
				if (lev_s < q) {
					error_src = "per il barile "+src+" la quantità da prelevare non deve essere superiore a "+lev_s+".";
				}
			}
			if(q!=0 && dst!=null){
				// check: il livello di riempimento non deve superare quello massimo del barile
				bar_d = Integer.parseInt(dst);
				bar_d-=1; // devo accedere a una lista e usare bar come indice
				max = Integer.parseInt(barile[2].getData(bar_d));//nella terza colonna c'è il maxlivello
				lev_d = Integer.parseInt(barile[3].getData(bar_d));//nella quarta colonna c'è il livello
				int diff = max - lev_d;
				if (diff < q) {
					error_dst = "La quantità da versare nel barile "+dst+" non deve essere superiore a "+diff+".";
				}
			}
		}
		if(error_vuoto!=null) s = s +"<br>"+ error_vuoto;
		if(error_sel!=null) s = s +"<br>"+ error_sel;
		if(error_q!=null) s = s +"<br>"+ error_q;
		if(error_src!=null) s = s +"<br>"+ error_src;
		if(error_dst!=null) s = s +"<br>"+ error_dst;
		return s;
	}


}
