package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;

public final class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("babbles", null);
		request.getRequestDispatcher("/search.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");

		if (selectedPage.equals("search")) {
			String text = request.getParameter("search");
			request.setAttribute("babbles", Application.getInstance().search(text));
			request.setAttribute("text", text);
			request.getRequestDispatcher("/search.ftl").forward(request, response);
		} else {
			request.setAttribute("errormessage", "Unknown command!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		}
		doGet(request, response);
	}
}
