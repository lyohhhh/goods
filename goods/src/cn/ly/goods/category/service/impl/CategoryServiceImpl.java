package cn.ly.goods.category.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ly.goods.category.dao.CategoryDao;
import cn.ly.goods.category.po.Category;
import cn.ly.goods.category.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categorydao;

	public List<Category> findAll() {
		// TODO Auto-generated method stub
		try {
			return categorydao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void add(Category category) {
		// TODO Auto-generated method stub
		try {
			categorydao.add(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public List<Category> findParents() {
		// TODO Auto-generated method stub
		try {
			return categorydao.findParents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public Category load(String cid) {
		// TODO Auto-generated method stub
		try {
			return categorydao.load(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void edit(Category category) {
		// TODO Auto-generated method stub
		try {
			categorydao.edit(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public int findChildCountByParent(String pid) {
		// TODO Auto-generated method stub
		try {
			return categorydao.findChildCountByParent(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void delete(String cid) {
		// TODO Auto-generated method stub
		try {
			categorydao.delete(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
