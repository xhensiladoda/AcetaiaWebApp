package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet che gestisce l'operazione di Misurazione
 */
@WebServlet("/Misurazione")
public class Misurazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBList barile[];     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Misurazione() {
    	super();
    }

	/**
	 * recupera le informazioni presenti nella tabella del DB barile e le invia alla jsp per la visualizzazione
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DBRetrieve b=new DBRetrieve("misurazione");
		barile=b.show();
		int row = b.getRows();
		int col=b.getCols(); 
		HttpSession session = request.getSession(true);
		session.setAttribute("r",row-1); // righe
		session.setAttribute("c",col-3); // colonne (non saranno visualizzate le ultime due per semplicità di visualizzazione)
		session.setAttribute("o",barile);
		response.sendRedirect("Misurazione.jsp");
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
		String idBarile = null;
		String tipo = null;
		String valore = null; 
		// retrieves params from jsp
		idBarile=request.getParameter("sel");
		tipo=request.getParameter("parametro");
		valore=request.getParameter("valore");
		String error = "";
		error = check(idBarile, tipo, valore);
		HttpSession session = request.getSession(true);
		if(error!=""){
			session.setAttribute("s",error);
			response.sendRedirect("Errore.jsp");
		} else {
			DBRetrieve dbq = new DBRetrieve("misurazione");
			int bar = 0;
			bar = Integer.parseInt(idBarile);
			bar-=1; // devo accedere a una lista e usare bar come indice
			int batt = Integer.parseInt(barile[5].getData(bar)); //nella sesta colonna c'è la batteria
			Object[] param = {idBarile, batt, tipo, valore};
			dbq.setQuery(param);
			//
			response.sendRedirect("Successo.jsp");
		}
		
	}
	
	/**
	 * verifica la presenza di errori nei parametri passati
	 * @param idBarile identificatore del barile
	 * @param tipo parametro misurato
	 * @param valore valore rilevato
	 * @return una stringa contenente i possibili errori; se vuota non ci sono errori
	 */
	private String check(String idBarile, String tipo, String valore) {
		String s = "";
		String error_vuoto = null;
		String error_val = null;
		String error_lev = null;
		int v = 0;
		int max = 0;
		int bar = 0;
		// check: ogni campo è compilato
		if(idBarile==null||tipo.contentEquals("empty")||valore=="") {
			error_vuoto = "Attenzione, è necessario compilare tutti i campi!";
		}
		if(valore!="") {
			// check: valore deve essere un numero
			try {
				v = Integer.parseInt(valore);
				if(v<=0) error_val = "Il valore deve essere un intero positivo.";
			} catch (NumberFormatException e) {
				error_val = "Il valore inserito deve essere un intero.";
			}
			if(idBarile!=null) {
				bar = Integer.parseInt(idBarile);
				bar-=1; // devo accedere a una lista e usare bar come indice
			}
			if(tipo.contentEquals("livello") && v!=0 && idBarile!=null){
				// check: il livello di riempimento non deve superare quello massimo del barile
				max = Integer.parseInt(barile[2].getData(bar));//nella terza colonna c'è il maxlivello
				if (max < v) {
					error_lev = "Per questo barile il livello non deve essere superiore a "+max+".";
				}
			}
		}
		if(error_vuoto!=null) s = s +"<br>"+ error_vuoto;
		if(error_val!=null) s = s +"<br>"+ error_val;
		if(error_lev!=null) s = s +"<br>"+ error_lev;
		return s;
	}

}
