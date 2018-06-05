package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.User;

/**
 * Einfaches Beispiel, das die Vewendung der Template-Engine zeigt.
 */
public final class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Put the user list in request and let freemarker paint it.
       // request.setAttribute("users", userList);

        request.getRequestDispatcher("/index.ftl").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");
    	
    	if(selectedPage.equals("login")) {
    		User user = new User(request.getParameter("username"), "", 
    				request.getParameter("status"), request.getParameter("name"));
    		if(Application.getInstance().login(user))
    			;
    		else {
    			request.setAttribute("errormessage", "registration failed!");
				request.getRequestDispatcher("/error.ftl").forward(request, response);
    		}
    	} else if (selectedPage.equals("register")) {
    		User user = new User(request.getParameter("username"), "", 
    				request.getParameter("status"), request.getParameter("name"));
    		if(Application.getInstance().register(user))
    			;
    		else {
    			request.setAttribute("errormessage", "registration failed!");
				request.getRequestDispatcher("/error.ftl").forward(request, response);
    		}
    	} else {
    		
    	}
    	
        doGet(request, response);
    }
}
