package com.dxc.lms.model;

import java.io.Serializable;


import java.util.Locale.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQueries({
	@NamedQuery(name="allBooksQry", query="SELECT b FROM Book b"),
	@NamedQuery(name="bookQry", query="SELECT b FROM Book b WHERE b.bcode=:bcode")
})

@Entity
@Table(name="books")
public class Book implements Serializable {
	
	@Id
	@Column(name="icode")
	private int bcode;
	
	@Column(name="title",nullable=false)
	private String title;
	
	@Column(name="author",nullable=false)
	private String author;

	
	public Book() {
		//left unimp
	}

	public Book(int bcode, String title, String author) {
		super();
		this.bcode = bcode;
		this.title = title;
		this.author = author;
	
	}

	public int getBcode() {
		return bcode;
	}

	public void setBcode(int bcode) {
		this.bcode = bcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
