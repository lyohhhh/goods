package cn.ly.goods.category.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.category.dao.CategoryDao;
import cn.ly.goods.category.po.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	
	@Autowired
	private QueryRunner qr;

	public List<Category> findAll() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		List<Category> parents = toCategoryList(mapList);
		
		for( Category parent : parents ){
			List<Category> children = findByParent(parent.getCid());
			parent.setChildren(children);
		}
		
		return parents;
	}
	
	
	
	
	public List<Category> findByParent(String pid) throws SQLException{
		
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),pid);
		
		return toCategoryList(mapList);
	}

	public void add(Category category) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		
		String pid = null;
		if(category.getParent() != null){
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(),category.getCname(),pid,category.getDesc()};
		qr.update(sql,params);
	}

	public List<Category> findParents() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler());
		return toCategoryList(mapList);
	}
	





	public Category load(String cid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	


	public void edit(Category category) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_category set cname=?,pid=?,`desc`=? where cid=?";
		String pid =null;
		if(category.getParent() != null){
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		qr.update(sql,params);
	}
	private List<Category> toCategoryList(List<Map<String,Object>> mapList){
		 List<Category> categoryList = new ArrayList<Category>();
		 for( Map<String, Object> map : mapList){
			 Category c = toCategory(map);
			 categoryList.add(c);
		 }
		 return categoryList;
	}
	
	private Category toCategory(Map<String,Object> map){
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");
		
		if(pid != null){
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		
		return category;
	}




	public int findChildCountByParent(String pid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(),pid);
		return cnt ==null? 0: cnt.intValue();
	}




	public void delete(String cid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from t_category where cid=?";
		
		qr.update(sql,cid);
	}



}
