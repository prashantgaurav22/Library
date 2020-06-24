package com.dxc.lms.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dxc.lms.exception.LibraryException;
import com.dxc.lms.model.Book;

public class BookDAOImpl implements BookDAO {
	
	private static final String DATA_FILE_NAME = "libraryData.dat";
	
	private Map<Integer,Book> booksMap;
	
	public BookDAOImpl() throws LibraryException {
		File file = new File(DATA_FILE_NAME);
		if(file.exists()) {
			try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))){
				booksMap = (Map<Integer, Book>) oin.readObject();
			}catch(IOException | ClassNotFoundException exp) {
				booksMap=new TreeMap<Integer, Book>();
				throw new LibraryException("Cant read data");
				
			}
		}else {
			booksMap=new TreeMap<Integer, Book>();
		}
	}
	private void saveData() throws LibraryException {
		try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))){
			oout.writeObject(booksMap);
		}catch(IOException exp) {
			throw new LibraryException("Cant save data");
		}
	}

	@Override
	public void addBook(Book book) throws LibraryException {
		if(book!=null) {
			booksMap.put(book.getBcode(), book);
			saveData();
		}else {
			throw new LibraryException("Null values cant be stored");
		}

	}

	@Override
	public void deleteBook(int bcode) throws LibraryException {
		if(booksMap.containsKey(bcode)) {
			booksMap.remove(bcode);
			saveData();
		}else {
			throw new LibraryException("Book"+bcode+ "not found");
		}

	}

	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return new ArrayList<>(booksMap.values());
	}

	@Override
	public Book getBookByBcode(int bcode) {
		// TODO Auto-generated method stub
		return booksMap.get(bcode);
	}

}
