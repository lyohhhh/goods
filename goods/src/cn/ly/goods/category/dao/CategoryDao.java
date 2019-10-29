package cn.ly.goods.category.dao;

import java.sql.SQLException;
import java.util.List;

import cn.ly.goods.category.po.Category;

public interface CategoryDao {
	/*
	 *	 返回所有
	 */
	public List<Category> findAll() throws SQLException;
	
	public void add(Category category) throws SQLException;
	
	public List<Category> findParents() throws SQLException;
	
	public Category load(String cid) throws SQLException;
	
	public void edit(Category category) throws SQLException;
	
	public int findChildCountByParent(String pid) throws SQLException;
	
	public void delete(String cid) throws SQLException;
	

}
