package cn.ly.goods.book.service;

import cn.ly.goods.book.po.Book;
import cn.ly.goods.pager.PageBean;

public interface BookService {
	public PageBean<Book> findByCategory(String cid,int pc);
	
	public PageBean<Book> findByBname(String bname,int pc);
	
	public PageBean<Book> findByAuthor(String author,int pc);
	
	public PageBean<Book> findByPress(String press,int pc);
	
	public PageBean<Book> findByCombination(Book criteria,int pc);
	
	public Book load(String bid);
	
	public int findBookCountByCategory(String cid);
	
	public void edit(Book book);
	
	public void delete(String bid);
	
	public void add(Book book);
}
