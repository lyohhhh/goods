package cn.ly.goods.book.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ly.goods.book.dao.BookDao;
import cn.ly.goods.book.po.Book;
import cn.ly.goods.book.service.BookService;
import cn.ly.goods.pager.PageBean;


@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;

	public PageBean<Book> findByCategory(String cid, int pc) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findByCategory(cid, pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public PageBean<Book> findByBname(String bname, int pc) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findByBname(bname, pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public PageBean<Book> findByAuthor(String author, int pc) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findByAuthor(author, pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public PageBean<Book> findByPress(String press, int pc) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findByPress(press, pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public PageBean<Book> findByCombination(Book criteria, int pc) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findByCombination(criteria, pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public Book load(String bid) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findBybid(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public int findBookCountByCategory(String cid) {
		// TODO Auto-generated method stub
		try {
			return bookDao.findBookCountByCategory(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void edit(Book book) {
		// TODO Auto-generated method stub
		try {
			bookDao.edit(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void delete(String bid) {
		// TODO Auto-generated method stub
		try {
			bookDao.delete(bid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void add(Book book) {
		// TODO Auto-generated method stub
		try {
			bookDao.add(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	} 
}
