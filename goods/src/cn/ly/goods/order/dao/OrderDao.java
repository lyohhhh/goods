package cn.ly.goods.order.dao;

import java.sql.SQLException;

import cn.ly.goods.order.po.Order;
import cn.ly.goods.pager.PageBean;

public interface OrderDao {
	
	public void add(Order order) throws SQLException;
	
	public PageBean<Order> findByUser(String uid,int pc) throws SQLException;
	
	public Order load(String oid) throws SQLException;
	
	public void updateStatus(String oid,int status) throws SQLException;
	
	public int findStatus(String oid) throws SQLException;
	
	public PageBean<Order> findAll(int pc) throws SQLException;
	
	public PageBean<Order> findBystatus(int status,int pc) throws SQLException;
}
