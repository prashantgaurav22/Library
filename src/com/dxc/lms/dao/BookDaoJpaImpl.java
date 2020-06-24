package com.dxc.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.dxc.lms.exception.LibraryException;
import com.dxc.lms.model.Book;

public class BookDaoJpaImpl implements BookDAO {
	
	static{
		em = Persistence.createEntityManagerFactory("mysqlPU").createEntityManager();		
	}
	
	private static EntityManager em;

	@Override
	public void addBook(Book book) throws LibraryException {
		if(book!=null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			em.persist(book);
			txn.commit();
			}

	}

	@Override
	public void deleteBook(int bcode) throws LibraryException {
		EntityTransaction txn = em.getTransaction();
		Book book = em.find(Book.class,bcode);
		if(book!=null) {
			txn.begin();
			em.remove(book);
			txn.commit();	
			//em.flush();        //to send all the db trans immediately without waiting for entitymanager
		}else {
			throw new LibraryException("No such book found");
		}

	}

	@Override
	public List<Book> getAllBooks() throws LibraryException {
		TypedQuery<Book> qry = em.createNamedQuery("allBooksQry", Book.class);
		return qry.getResultList();
	}

	@Override
	public Book getBookByBcode(int bcode) throws LibraryException {
		TypedQuery<Book> qry = em.createNamedQuery("bookQry",Book.class);
		qry.setParameter("bcode",bcode);
		
		return qry.getFirstResult()>0 ? qry.getSingleResult():null;
	}

	}
