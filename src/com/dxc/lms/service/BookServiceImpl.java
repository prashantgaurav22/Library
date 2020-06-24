package com.dxc.lms.service;

import java.util.ArrayList;
import java.util.List;

import com.dxc.lms.dao.BookDAO;
import com.dxc.lms.dao.BookDaoJpaImpl;
import com.dxc.lms.exception.LibraryException;
import com.dxc.lms.model.Book;

public class BookServiceImpl implements BookService {
	
	private BookDAO bookDao;
	
	public BookServiceImpl() throws LibraryException {
		this.bookDao = new BookDaoJpaImpl();
	}
	
	private boolean isValidBcode(int bcode) throws LibraryException {
		return bcode>0 && (bookDao.getBookByBcode(bcode)==null);
		
	}
	private boolean isValidTitle(String title) {
		return title!=null && title.length()>5 && title.length()<20;
		
	}
	private boolean isValidAuthor(String author) {
		return author!=null && author.length()>3 && author.length()<10;
	
	}
	
	private boolean isValidBook(Book book) throws LibraryException {
	       
        boolean isValid=true;
       
        if(book==null) {
            isValid=false;
            throw new LibraryException("Book value can not be null");
        }
		List<String> errMsgs = new ArrayList<String>();
		
		if(!isValidBcode(book.getBcode())){
			errMsgs.add("Error: Code cant be negative or repeated");			
		}
		if(!isValidTitle(book.getTitle())){
			errMsgs.add("Error: Title cant be less than 6 chars");			
		}
		if(!isValidAuthor(book.getAuthor())){
			errMsgs.add("Error: Author name cant be less than 3 chars");	
		}
		
		if(errMsgs.size()>0) {
			isValid=false;
			throw new LibraryException(errMsgs.toString());
		}
		return isValid;
	}
	

	@Override
	public void addBook(Book book) throws LibraryException {
		if(isValidBook(book)) {
			bookDao.addBook(book);
		}

	}

	@Override
	public void deleteBook(int bcode) throws LibraryException {
		bookDao.deleteBook(bcode);

	}

	@Override
	public List<Book> getAllBooks() throws LibraryException {
		return bookDao.getAllBooks();
	}

	@Override
	public Book getBookByBcode(int bcode) throws LibraryException {
		return bookDao.getBookByBcode(bcode);
	}

}
