package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.domain.User;

public final class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = Application.getUser();

		String profile = request.getParameter("username");
		if (Application.getInstance().getUser(profile) == null) {
			request.setAttribute("errormessage", "Unknown user!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		} else {
			User thisUser = Application.getInstance().getUser(profile);
			user.setName("you");

			request.setAttribute("messages", Application.getInstance().getMessages(thisUser));

			request.setAttribute("user", user);
			request.setAttribute("otheruser", thisUser);
			request.getRequestDispatcher("/chat.ftl").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");
		String profile = request.getParameter("username");
		if (Application.getInstance().getUser(profile) == null) {
			request.setAttribute("errormessage", "Unknown user!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		} else if (selectedPage.equals("send")) {
			Message message = new Message();
			message.setRecipiant(profile);
			message.setSender(Application.getUser().getUsername());
			message.setText(request.getParameter("text"));
			Application.getInstance().sendMessage(message);
			// if it is not any of the known functions go to error.
		} else {
			request.setAttribute("errormessage", "Unknown command!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		}
		doGet(request, response);
	}
}
