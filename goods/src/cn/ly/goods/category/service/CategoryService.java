package cn.ly.goods.category.service;

import java.util.List;

import cn.ly.goods.category.po.Category;

public interface CategoryService {
	
	public List<Category> findAll();
	
	public void add(Category category);
	
	public List<Category> findParents();
	
	public Category load(String cid);
	
	public void edit(Category category);
	
	public int findChildCountByParent(String pid);
	
	public void delete(String cid);
	

}
