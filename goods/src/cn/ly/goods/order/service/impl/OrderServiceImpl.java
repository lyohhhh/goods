package cn.ly.goods.order.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.jdbc.JdbcUtils;
import cn.ly.goods.order.dao.OrderDao;
import cn.ly.goods.order.po.Order;
import cn.ly.goods.order.service.OrderService;
import cn.ly.goods.pager.PageBean;


@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;

	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		
	}
	public void create(Order order) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
		
	}
	public PageBean<Order> myOrders(String uid, int pc) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
	}
	public Order load(String oid) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
	}
	public void updateStatus(String oid, int status) {
		// TODO Auto-generated method stub
		try {
			orderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public int findStatus(String oid) {
		// TODO Auto-generated method stub
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public PageBean<Order> findAll(int pc) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll(pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
	}
	public PageBean<Order> findByStatus(int status, int pc) {
		// TODO Auto-generated method stub
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findBystatus(status, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
	}
}
