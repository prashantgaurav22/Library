package com.dxc.lms.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dxc.lms.exception.LibraryException;
import com.dxc.lms.model.Book;
import com.dxc.lms.service.BookService;
import com.dxc.lms.service.BookServiceImpl;

/**
 * Servlet implementation class BookController
 */
@WebServlet({"/viewBooks","/addBook","/confirmAddBook","/deleteBook"})
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		String view = null;
		
		try {
		BookService bookService = new BookServiceImpl();
		
		switch(path) {
		case "/viewBooks":
			view="booksPage.jsp";
			request.setAttribute("books",bookService.getAllBooks());
			break;
		case "/addBook":
			view = "bookFormPage.jsp";
			break;
		case "/confirmAddBook":
			Book book = new Book();
			book.setBcode(Integer.parseInt(request.getParameter("bcode")));
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			bookService.addBook(book);
			view="index.jsp";
			request.setAttribute("msg","Book is saved with bcode: " +book.getBcode());
			break;
		case "/deleteBook":
			int bcode = Integer.parseInt(request.getParameter("bcode"));
			bookService.deleteBook(bcode);
			view="index.jsp";
			request.setAttribute("msg","Book is deleted with bcode: " +bcode);
			break;		
			
			
		}
		}catch(LibraryException e){
			view = "errorPage.jsp";
			request.setAttribute("errMsg", e.getMessage());
		}
		request.getRequestDispatcher(view).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
