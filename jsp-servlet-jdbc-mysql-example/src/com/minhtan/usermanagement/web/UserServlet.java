package com.minhtan.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minhtan.usermanagement.dao.UserDao;
import com.minhtan.usermanagement.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "UserServlet", urlPatterns = "/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			insertUser(request, response);
			break;
		case "/update":
			updateUser(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/edit":
			showEditForm(request, response);
			break;
		default:
			listUser(request, response);
			break;
		}
	}

	// return list of users to view layer
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> users = new ArrayList();
		users = userDao.selectAllUsers();
		request.setAttribute("listUser", users);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user-list.jsp");
		requestDispatcher.forward(request, response);
	}

	// display user-form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
		requestDispatcher.forward(request, response);
	}

	// handle create user request
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		userDao.insertUser(new User(name, email, country));
		response.sendRedirect("list");
	}

	// handle edit user
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
		request.setAttribute("user", existingUser);
		requestDispatcher.forward(request, response);
	}

	// handle update user request
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		userDao.updateUser(new User(id, name, email, country));
		response.sendRedirect("list");
	}

	// handle delete request
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("list");
	}

}
