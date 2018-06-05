package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.Babble;

public final class CreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/create.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");

		if (selectedPage.equals("create")) {
			String text = request.getParameter("value");
			String username = Application.getUser().getUsername();

			if (text.length() > 280) {
				request.setAttribute("errormessage", "Babbled more than 280 character!!");
				request.getRequestDispatcher("/error.ftl").forward(request, response);
			} else {
				Babble babble = new Babble(0, text, username, "11");
				Application.getInstance().createBabble(babble);
				request.setAttribute("username", username);
				response.sendRedirect("/profile?username=" + Application.getUser().getUsername());
			}
		} else {
			request.setAttribute("errormessage", "Unknown command!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		}
		doGet(request, response);
	}
}
