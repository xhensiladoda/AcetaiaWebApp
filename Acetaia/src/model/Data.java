package model;
import java.util.Date;

/**
 * imposta la data in cui viene effettuata l'operazione nel formato richiesto
 */
public class Data {

	private int g = 0;
	private int m = 0;
	private int a = 0;
	
	/**
	 * imposta la data a oggi
	 */
	public Data() {
		Date date = new Date();
		g = date.getDate();
		m = date.getMonth();
		m+=1;// getMonth() between 0 and 11
		a = date.getYear();
		a+=1900; // getYear() returns current year - 1900
	}
	
	/**
	 * mette la data nel formato richiesto
	 * @return una stringa che rappresenta la data
	 */
	public String getDate() {
		String s = a+"-"+m+"-"+g;
		return s;
	}

}
