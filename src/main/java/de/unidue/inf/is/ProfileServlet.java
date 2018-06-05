package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.User;

public final class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//User user = Application.getUser();

		String profile = request.getParameter("username");
		if (Application.getInstance().getUser(profile) == null) {
			request.setAttribute("errormessage", "Unknown user!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		} else {
			User thisUser = Application.getInstance().getUser(profile);

			// request.setAttribute("follow", Application.getInstance().getFollow(user,
			// thisUser));
			// request.setAttribute("block", Application.getInstance().getBlock(user,
			// thisUser));
			if (Application.getInstance().getBlock(thisUser) != null) {
				request.setAttribute("blocks", Application.getInstance().getBlock(thisUser));
			} else {
				request.setAttribute("babbles", Application.getInstance().getTimeline(thisUser));
			}
			// thisUser = new User("admin", "pp", "status", "name");
			request.setAttribute("user", thisUser);
			request.getRequestDispatcher("/profile.ftl").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage = (request.getParameter("page") == null) ? "" : request.getParameter("page");

		if (selectedPage.equals("follow")) {
			User user = Application.getInstance().getUser(request.getParameter("username"));
			System.out.println("user is trying to follow/unfollow the following user: " + user.getUsername());
			int value = Application.getInstance().follow(user);
			if (value == 1) {
				request.setAttribute("username", user.getUsername());
				request.setAttribute("message", "following the user now!");
				request.getRequestDispatcher("/profile_display.ftl").forward(request, response);
			} else {
				request.setAttribute("username", user.getUsername());
				request.setAttribute("message", "no longer following the user!");
				request.getRequestDispatcher("/profile_display.ftl").forward(request, response);
			}
		} else if (selectedPage.equals("block")) {
			User user = Application.getInstance().getUser(request.getParameter("username"));
			System.out.println("user is trying to block/unblock the following user: " + user.getUsername());
			int value = Application.getInstance().block(user, request.getParameter("reason"));
			if (value == 1) {
				request.setAttribute("username", user.getUsername());
				request.setAttribute("message", "User has been blocked!");
				request.getRequestDispatcher("/profile_display.ftl").forward(request, response);
			} else {
				request.setAttribute("username", user.getUsername());
				request.setAttribute("message", "you have unblocked the user!");
				request.getRequestDispatcher("/profile_display.ftl").forward(request, response);
			}
		} else {
			request.setAttribute("errormessage", "Unknown command!");
			request.getRequestDispatcher("/error.ftl").forward(request, response);
		}
		doGet(request, response);
	}
}
