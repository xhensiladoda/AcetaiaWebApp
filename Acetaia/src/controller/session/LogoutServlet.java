package controller.session;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
    }
    
    /**
     * distrugge la sessione e re-indirizza alla pagina di login
     * @param request richiesta HTTP
     * @param response risposta HTTP
     * @throws ServletException eccezione generica riscontrata dalla servlet
     * @throws IOException eccezione IO
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	HttpSession session=request.getSession(true);
            Enumeration<String> attributeNames=session.getAttributeNames(); //get all attributeNames associated with session
            while(attributeNames.hasMoreElements()) { //destroy session
                String name=(String)attributeNames.nextElement();
                session.removeAttribute(name);
            }
        } finally {    
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
