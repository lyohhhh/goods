package cn.ly.goods.book.dao;

import java.sql.SQLException;

import cn.ly.goods.book.po.Book;
import cn.ly.goods.pager.PageBean;

public interface BookDao {

	public PageBean<Book> findByCategory(String cid, int pc) throws SQLException;
	//
	public PageBean<Book> findByBname(String bname,int pc) throws SQLException;
	
	public PageBean<Book> findByAuthor(String author,int pc) throws SQLException;
	
	public PageBean<Book> findByPress(String press,int pc) throws SQLException;
	
	public PageBean<Book> findByCombination(Book criteria,int pc) throws SQLException;
	
	public Book findBybid(String bid) throws SQLException;
	
	public int findBookCountByCategory(String cid) throws SQLException;
	
	public void edit(Book book) throws SQLException;
	
	public void delete(String bid) throws SQLException;
	
	public void add(Book book) throws SQLException;
}
