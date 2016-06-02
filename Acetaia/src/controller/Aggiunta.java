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
 * Servlet che gestisce l'operazione di Aggiunta
 */
@WebServlet("/Aggiunta")
public class Aggiunta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBList barile[];
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Aggiunta() throws SQLException {
        super();
    }
    
    /**
     * recupera le informazioni presenti nella tabella del DB barile e le invia alla jsp per la visualizzazione
     * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DBRetrieve b=new DBRetrieve("aggiunta");
		barile=b.show();
		int row = b.getRows();
		int col=b.getCols();
		HttpSession session = request.getSession(true);
		session.setAttribute("r",row-1); // righe
		session.setAttribute("c",col-3); // colonne (non saranno visualizzate le ultime due per semplicit� di visualizzazione)
		session.setAttribute("o",barile);
		response.sendRedirect("Aggiunta.jsp");
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
		String idBarile;
		String tipo;
		String quantita;
		// retrieves params from jsp
		idBarile=request.getParameter("sel");
		tipo=request.getParameter("tipo");
		quantita=request.getParameter("quantita");
		String error = "";
		error = check(idBarile, tipo, quantita);
		HttpSession session = request.getSession(true);
		if(error!=""){
			session.setAttribute("s",error);
			response.sendRedirect("Errore.jsp");
		} else {
			DBRetrieve dbq = new DBRetrieve("aggiunta");
			int bar = 0;
			bar = Integer.parseInt(idBarile);
			bar-=1; // devo accedere a una lista e usare bar come indice
			int lev = 0;
			lev = Integer.parseInt(barile[3].getData(bar));//nella quarta colonna c'� il livello
			int q = 0;
			q = Integer.parseInt(quantita);
			int batt = Integer.parseInt(barile[5].getData(bar));//nella sesta colonna c'� la batteria
			int newLev = lev+q;
			Object[] param = {idBarile, batt, tipo, quantita, newLev};
			dbq.setQuery(param);
			//
			response.sendRedirect("Successo.jsp");
			
		}
	}
	
	/**
	 * verifica la presenza di errori nei parametri passati
	 * @param idBarile identificatore del barile
	 * @param tipo tipo di prodotto da aggiungere
	 * @param quantita quantit� di prodotto aggiunto
	 * @return una stringa contenente i possibili errori; se vuota non ci sono errori
	 */
	private String check(String idBarile, String tipo, String quantita) {
		String s = "";
		String error_vuoto = null;
		String error_q = null;
		String error_lev = null;
		int q = 0;
		int max = 0;
		int lev = 0;
		int bar = 0;
		// check: ogni campo � compilato
		if(idBarile==null||tipo==""||quantita=="") {
			error_vuoto = "Attenzione, � necessario compilare tutti i campi!";
		}
		if(quantita!="") {
			// check: quantit� deve essere un numero
			try {
				q = Integer.parseInt(quantita);
				if(q<=0) error_q = "La quantit� deve essere un valore intero positivo.";
			} catch (NumberFormatException e) {
				error_q = "La quantit� deve essere un valore intero.";
			}
			if(q!=0 && idBarile!=null){
				// check: il livello di riempimento non deve superare quello massimo del barile
				bar = Integer.parseInt(idBarile);
				bar-=1; // devo accedere a una lista e usare bar come indice
				max = Integer.parseInt(barile[2].getData(bar));//il maxlivello � la terza colonna
				lev = Integer.parseInt(barile[3].getData(bar));//il livello � la quarta colonna
				int diff = max - lev;
				if ((diff < q) && (q!=0) ) {
					error_lev = "Per questo barile la quantit� non deve essere superiore a "+diff+".";
				}
			}
		}
		if(error_vuoto!=null) s = s +"<br>"+ error_vuoto;
		if(error_q!=null) s = s +"<br>"+ error_q;
		if(error_lev!=null) s = s +"<br>"+ error_lev;
		return s;	
	}


}
