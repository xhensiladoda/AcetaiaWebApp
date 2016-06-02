package controller.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super(); 
    }
    
    /**
     * controlla che username e password inseriti siano validi; in caso positivo re-indirizza alla welcome page
     * altrimenti di nuovo alla pagina del login
     * @param req richiesta HTTP
     * @param res risposta HTTP
     * @throws ServletException eccezione generica riscontrata dalla servlet
     * @throws IOException eccezione IO
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) res;
    	String username=request.getParameter("username");
		String password=request.getParameter("password");
		DBUser user=new DBUser(username);
		String user_password=user.getPassword();
        if(password.contentEquals(user_password)) { //password corretta
        	// si crea la sessione
	        HttpSession session = request.getSession(true); 
	        session.setAttribute("username",username);
	        session.setAttribute("password",password);
	        session.setAttribute("valid","yes");            
	        response.sendRedirect("Welcome.jsp");
		} else {
			 response.sendRedirect("index.jsp");
		}
    }
    
    /**
     * invoca processRequest
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    /**
     * invoca processRequest
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	processRequest(request, response);
    }
 
    
    
}

