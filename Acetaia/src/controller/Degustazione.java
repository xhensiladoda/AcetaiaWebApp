package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import model.*;

/**
 * Servlet che gestisce l'operazione di Degustazione
 */
@WebServlet("/Degustazione")
@MultipartConfig
public class Degustazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBList barile[];
	private String path = "C:/Users/Chiara/Desktop/Magistrale/da fare/Sistemi e Applicazioni di Rete/Progetto/ws_kepler/Acetaia/WebContent/degustazione/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Degustazione() throws SQLException{
        super();
        new DBConnection();
    }

	/**
	 * recupera le informazioni presenti nella tabella del DB barile e le invia alla jsp per la visualizzazione
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			DBRetrieve b=new DBRetrieve("degustazione");
			barile=b.show();
			int row = b.getRows();
			int col=b.getCols(); 
			HttpSession session = request.getSession(true);
			session.setAttribute("r",row-1); // righe
			session.setAttribute("c",col-3); // colonne (non saranno visualizzate le ultime due per semplicità di visualizzazione)
			session.setAttribute("o",barile);
			response.sendRedirect("Degustazione.jsp");
	}

	/**
	 * prende i dati e l'immagine inseriti dall'utente nella jsp, li invia alla classe che si occupa di fare 
	 * le query al DB e re-indirizza l'utente verso una pagina di successo se  i dati erano corretti, a una di 
	 * errore in caso contrario
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename=null;
		String idBarile=null;
		String error=null;
		int bar = 0;
		//
		List<FileItem> images = null;
		try {
			images = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);    
		} catch (FileUploadException e) {
		            throw new ServletException("Cannot parse multipart request.", e);
		  }
		for (FileItem item : images) {
			if (item.isFormField()) {
                idBarile = item.getString(); // get value of radio button
                bar = Integer.parseInt(idBarile);
				bar-=1; 
			}	     
		     else {  
    			// Process uploaded fields here.
    			// Get the file name
                filename = FilenameUtils.getName(item.getName());
                error=check(idBarile, filename,item);
		      }
		}
		HttpSession session = request.getSession(true);
		if(error!=""){ // almeno un errore
			session.setAttribute("s",error);
			response.sendRedirect("Errore.jsp");
		}
		else{
			int batt = Integer.parseInt(barile[5].getData(bar));
			String total_path=path + filename;
			Object[] param = {idBarile, batt, total_path};
			DBRetrieve dbq = new DBRetrieve("degustazione");
			dbq.setQuery(param);
			//
			response.sendRedirect("Successo.jsp");
		}
	}
	
	/**
	 * verifica la presenza di errori nei parametri passati
	 * @param barile identificatore del barile
	 * @param file il file caricato dall'utente
	 * @param item oggetto che rappresenta il file ricevuto nel doPost
	 * @return una stringa contenente i possibili errori; se vuota non ci sono errori
	 */
	private String check(String barile, String file, FileItem item){
		String error_barile=null;
		String error_file=null;
		String error_path=null;
		String error_file_esistente=null;
		String error_nessun_file=null;
		String s="";
		if(barile==null)
			error_barile="Nessun barile selezionato.";
		if(file!=""){
            if((file.endsWith("jpeg")!=true)&&(file.endsWith("jpg")!=true)&&(file.endsWith("png")!=true))
            	error_file="Il file selezionato deve avere estensione JPEG o PNG.";
            else{
            	if(file.length()>=300)
    				error_path="Il path del file è troppo lungo!";
            	else{
            		System.out.println("path "+path);
            		System.out.println("file "+file);
            		String newpath = path+file;
            		File file_new = new File(newpath); // Definisco file di destinazione
	                try {
	            		System.out.println("file_new "+file_new.getCanonicalPath());
	                	System.out.println(file_new.exists());
	                	if(file_new.exists())
	                		error_file_esistente="Esite già un file con questo nome. Rinominare il file!";
	                	else {
	                		System.out.println("sto per fare il write");
	                		if(error_barile==null) {
	                			System.out.println("sto per fare il write bis");
	                			item.write(file_new);
	                			System.out.println("fatto il write");
	                		}
	                	}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
		        }
           }
        } else error_nessun_file="Nessun file selezionato!";
		//
		if(error_barile!=null) s = s +"<br>"+ error_barile;
		if(error_file!=null) s = s +"<br>"+ error_file;
		if(error_path!=null) s = s +"<br>"+ error_path;
		if(error_file_esistente!=null) s = s +"<br>"+ error_file_esistente;
		if(error_nessun_file!=null) s = s +"<br>"+ error_nessun_file;
		System.out.println("s "+s);
		return s;
	}

}
