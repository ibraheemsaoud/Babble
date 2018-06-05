package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.User;

public final class BabbleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = Application.getUser();
		String id = request.getParameter("id");
		Babble babble = Application.getInstance().getBabble(id);
		if (babble == null) {
			request.setAttribute("errormessage", "Unknown babble!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		} else {
			User babbler = Application.getInstance().getUser(babble.getCreator());
			request.setAttribute("user", babbler);
			request.setAttribute("babble", babble);
			request.setAttribute("like", Application.getInstance().getLike(babble, user));
			if (Application.getInstance().getRebabbled(babble, user))
				request.setAttribute("rebabble", 1);
			else
				request.setAttribute("rebabble", 0);
			request.getRequestDispatcher("/babble.ftl").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");

		if (selectedPage.equals("like")) {
			Application.getInstance().like(Application.getInstance().getBabble(request.getParameter("id")));
			doGet(request, response);
		} else if (selectedPage.equals("dislike")) {
			Application.getInstance().dislike(Application.getInstance().getBabble(request.getParameter("id")));
			doGet(request, response);
		} else if (selectedPage.equals("rebabble")) {
			Application.getInstance().Rebabble(Application.getInstance().getBabble(request.getParameter("id")));
			doGet(request, response);
		} else if (selectedPage.equals("delete")) {
			if (!Application.getInstance()
					.deleteBabble(Application.getInstance().getBabble(request.getParameter("id")))) {
				request.setAttribute("errormessage", "you are not the original babbler!");
				request.getRequestDispatcher("/error.ftl").forward(request, response);
			}
			doGet(request, response);
		} else {
			request.setAttribute("errormessage", "Unknown command!" + request.getParameter("page"));
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		}
		doGet(request, response);
	}
}
