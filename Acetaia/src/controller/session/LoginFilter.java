package controller.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
 * per ogni pagina controlla se l'utente è autenticato; in caso positivo permette l'accesso altrimenti
 * rimanda alla pagina di autenticazione
 */
public class LoginFilter implements Filter {
 
    private ArrayList<String> urlsToAvoidList;
       
    /**
     * funzione che viene chiamata alla chiusura dell'applicazione
     */
    public void destroy() {
    }
    
    /**
     * verifica se l'utente può o meno accedere alla pagina
     * @param request la richiesta HTTP
     * @param response la risposta HTTP
     * @param chain oggetto che permette di visualizzare la catena di invocazioni della richiesta
     * @throws ServletException eccezione riscontrata dalla servlet
     * @throws IOException eccezione di IO
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;
        HttpServletResponse hsr=(HttpServletResponse)response;
        String url=req.getServletPath(); //get url from current request
//        System.out.println("url="+url); //print url-debugging purpose.. !!
        boolean allowedRequest=false;           
        //set cache-control header to disable caching (HTTP/1.1 spec)
        hsr.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        //set cache-control header to disable caching  (HTTP/1.0(older) spec).. because some old clients might not support the no-cache header above 
        hsr.setHeader("Pragma","no-cache");
        //set dateHeader to prevent caching at the proxy server
        hsr.setDateHeader("Expires",0); 
        // if url is login.jsp or LoginServlet or an external css (avoid-urls parameter)
        if(urlsToAvoidList.contains(url)) { // specified in init() method and web.xml
            allowedRequest = true;
        }
        if (!allowedRequest) { //for every other url
            HttpSession session = req.getSession(false); //returns null if session has expired or invalidated
//            System.out.println("La sessione con url="+url+" è "+session);
            if (session==null) { 
               resp.sendRedirect("index.jsp"); 
               return;
            } else 
            	if(session.getAttribute("valid")==null) {  // check for valid session.. see LoginServlet and LogoutServlet for more info
	               resp.sendRedirect("index.jsp"); // in this case also, redirect to index.jsp
	               return;
            	}
        }
        chain.doFilter(req, resp);
    }

    /**
     * funzione, chiamata soltanto una volta all'inizio, che imposta le pagine che non devono
     * essere controllate dal filtro
     * @param filterConfig 
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("avoid-urls"); //get the urls to avoid as supplied from web.xml
        StringTokenizer token = new StringTokenizer(urls, ","); //tokenize the continous strings, separated by ','
        urlsToAvoidList = new ArrayList<String>();      
        while (token.hasMoreTokens()) 
            urlsToAvoidList.add(token.nextToken()); //add to our urlList;
    }
    
    
}