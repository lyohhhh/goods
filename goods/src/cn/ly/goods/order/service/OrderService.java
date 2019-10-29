package cn.ly.goods.order.service;

import cn.ly.goods.order.po.Order;
import cn.ly.goods.pager.PageBean;

public interface OrderService {
	
	public void createOrder(Order order);
	
	public PageBean<Order> myOrders(String uid,int pc);
	
	public Order load(String oid);
	
	public void updateStatus(String oid,int status);
	
	public int findStatus(String oid);
	
	public PageBean<Order> findAll(int pc);
	
	public PageBean<Order> findByStatus(int status,int pc);
}
