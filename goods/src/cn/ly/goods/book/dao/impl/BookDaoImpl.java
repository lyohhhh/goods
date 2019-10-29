package cn.ly.goods.book.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.book.dao.BookDao;
import cn.ly.goods.book.po.Book;
import cn.ly.goods.category.po.Category;
import cn.ly.goods.pager.Expression;
import cn.ly.goods.pager.PageBean;
import cn.ly.goods.pager.PageConstants;


@Repository
public class BookDaoImpl implements BookDao{
	@Autowired
	private QueryRunner qr;

	public PageBean<Book> findByCategory(String cid, int pc)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid", "=", cid));
		return findByCriteria(exprList, pc);
	}
	public PageBean<Book> findByBname(String bname, int pc) throws SQLException {
		// TODO Auto-generated method stub
//		Map<String,Object> criteria = new HashMap<String, Object>();
//		criteria.put("bname", bname);
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%" + bname + "%"));
		return findByCriteria(exprList, pc);
	}

	public PageBean<Book> findByAuthor(String author, int pc)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author","like","%" + author + "%"));
		return findByCriteria(exprList, pc);
	}

	public PageBean<Book> findByPress(String press, int pc) throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press","like","%" + press + "%"));
		return findByCriteria(exprList, pc);
	}

	public PageBean<Book> findByCombination(Book criteria, int pc)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%" + criteria.getBname() + "%"));
		exprList.add(new Expression("author","like","%" + criteria.getAuthor() + "%"));
		exprList.add(new Expression("press","like","%" + criteria.getPress() + "%"));
		return findByCriteria(exprList, pc);
	}
	
	public PageBean<Book> findByCriteria(List<Expression> exprList, int pc)
			throws SQLException {
		// TODO Auto-generated method stub
		int ps = PageConstants.BOOK_PAGE_SIZE;
		 
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();
		
		for(Expression expr : exprList){
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			if(!expr.getOperator().equals("is null")){
				whereSql.append("?");
				params.add(expr.getValue());
			}
			
		}
		
		String sql = "select count(*) from t_book" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();
		
		sql = "select * from t_book" + whereSql + " order by orderBy limit ?,?";
		params.add((pc-1) * ps);
		params.add(ps);
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
		
		PageBean<Book> pb = new PageBean<Book>();
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		
		return pb;
	}
	public Book findBybid(String bid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_book b,t_category c where b.cid=c.cid and b.bid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),bid);
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		
		if(map.get("pid") != null){
			Category parent = new Category();
			parent.setCid((String)map.get("pid"));
			category.setParent(parent);
		}
		return book;
	}
	public int findBookCountByCategory(String cid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select count(*) from t_book where cid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(),cid);
		return cnt ==null? 0: cnt.intValue();
	}
	public void edit(Book book) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?,discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?,printtime=?,booksize=?,paper=?,cid=? where bid=?";
		Object[] params ={book.getBname(),book.getAuthor(),book.getPrice(),book.getCurrPrice(),book.getDiscount(),book.getPress(),book.getPublishtime(),book.getEdition(),book.getPageNum(),book.getWordNum(),book.getPrinttime(),book.getBooksize(),book.getPaper(),book.getCategory().getCid(),book.getBid()};
		qr.update(sql,params);
	}
	public void delete(String bid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from t_book where bid=?";
		qr.update(sql,bid);
	}
	public void add(Book book) throws SQLException  {
		// TODO Auto-generated method stub
		String sql = "insert into t_book(bid,bname,author,price,currPrice,discount,press,publishtime,edition,pageNum,wordNum,printtime,booksize,paper,cid,image_w,image_b)" +
		 "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),book.getPrice(),book.getCurrPrice(),book.getDiscount(),book.getPress(),book.getPublishtime(),book.getEdition(),book.getPageNum(),book.getWordNum(),book.getPrinttime(),book.getBooksize(),book.getPaper(),book.getCategory().getCid(),book.getImage_w(),book.getImage_b()};
		qr.update(sql,params);
	}
}
