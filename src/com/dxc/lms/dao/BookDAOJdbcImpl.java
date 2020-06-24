package com.dxc.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dxc.lms.exception.LibraryException;
import com.dxc.lms.model.Book;

public class BookDAOJdbcImpl implements BookDAO {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException exp) {
			System.out.println(exp.getMessage());
			System.exit(0);
		}
		
	}
	
	 private static final Logger logger = Logger.getLogger("BookDAO");
	    private static final String dbUrl = "jdbc:mysql://localhost:3306/dxcbatch";
	    private static final String dbUnm = "root";
	    private static final String dbPwd = "Banty@9825";
	
	private static final String INS_BOOK_QUERY="INSERT INTO book(bcode,title,author)VALUES(?,?,?)";
	private static final String DEL_BOOK_QUERY="DELETE FROM book WHERE bcode=?";
	private static final String SEE_ALL_BOOKS_QUERY="SELECT bcode,title,author FROM book";
	private static final String SEE_BOOK_BY_BCODE_QUERY="SELECT bcode,title,author FROM book WHERE bcode=?";
	

	@Override
	public void addBook(Book book) throws LibraryException {		
		if(book!=null) {
		try(Connection con=DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
			PreparedStatement pinsert=con.prepareStatement(INS_BOOK_QUERY);
			
			pinsert.setInt(1, book.getBcode());
			pinsert.setString(2, book.getTitle());
			pinsert.setString(3, book.getAuthor());
			
			pinsert.executeUpdate();
		    logger.info("Book added successfully!");
		    
		}catch(SQLException exp) {
			throw new LibraryException("Add operation failed"); 
		}
	}
}

	@Override
	public void deleteBook(int bcode) throws LibraryException {
		try(Connection con=DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
			PreparedStatement pdelete=con.prepareStatement(DEL_BOOK_QUERY);
			
			pdelete.setInt(1,bcode);
			
			pdelete.executeUpdate();
			   logger.info("Book deleted successfully!");
			   
		}catch(SQLException exp) {
			logger.error(exp.toString());
            throw new LibraryException("Sorry! Delete Book Operation Failed!"); 
		}
	}
	

	@Override
	public List<Book> getAllBooks() throws LibraryException {
		List<Book> books=new ArrayList<Book>();

		try(Connection con=DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
			PreparedStatement pselect=con.prepareStatement(SEE_ALL_BOOKS_QUERY);
			
			
			ResultSet rs = pselect.executeQuery();
			
			while(rs.next()) {
				Book book=new Book();
				
				book.setBcode(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				
				books.add(book);
			}
			 logger.info("Book retrived successfully!");
		}catch(SQLException exp) {
			throw new LibraryException("Sorry Couldnt retrieve data"); 
		}
		return books;
	}

	@Override
	public Book getBookByBcode(int bcode) throws LibraryException {
		Book book=null;
		
		try(Connection con=DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
			PreparedStatement pselect=con.prepareStatement(SEE_BOOK_BY_BCODE_QUERY);
			
			pselect.setInt(1, bcode);
			
			ResultSet rs = pselect.executeQuery();
			
			if(rs.next()) {
				book=new Book();
				book.setBcode(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
			}
			  logger.info("Book retrived successfully!");
			
		}catch(SQLException exp) {
			throw new LibraryException("Sorry! Couldnt retrieve data"); 
		}
		return book;
	}

}
