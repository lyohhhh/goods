package cn.ly.goods.order.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.book.po.Book;
import cn.ly.goods.order.dao.OrderDao;
import cn.ly.goods.order.po.Order;
import cn.ly.goods.order.po.OrderItem;
import cn.ly.goods.pager.Expression;
import cn.ly.goods.pager.PageBean;
import cn.ly.goods.pager.PageConstants;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	
	private QueryRunner qr;

	public void add(Order order) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		
		qr.update(sql,params);
		
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		 
		for( int i = 0; i < len; i++ ){
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[]{item.getOrderItemId(),
					item.getQuantity(),item.getSubtotal(),item.getBook().getBid(),item.getBook().getBname(),item.getBook().getCurrPrice(),
					item.getBook().getImage_b(),order.getOid()};
		}
		qr.batch(sql, objs);
	}

	public PageBean<Order> findByUser(String uid, int pc) throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid","=",uid));
		return findByCriteria(exprList,pc);
	}
	
	private PageBean<Order> findByCriteria(List<Expression> exprList,int pc) 
			throws SQLException{
		int ps = PageConstants.ORDER_PAGE_SIZE;
		
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();
		
		for( Expression expr : exprList){
			
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			if( !expr.getOperator().equals("is null")){
				
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		
		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();
		
		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1) * ps);
		params.add(ps);
		
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class),params.toArray());
		
		for( Order order : beanList){
			loadOrderItem(order);
		}
		
		PageBean<Order> pb = new PageBean<Order>();
		
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	
	}
	
	

	
	
	private List<OrderItem> toOrderItemList(List<Map<String,Object>> mapList){
			
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		for( Map<String,Object> map : mapList){
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
			
		}
		return orderItemList;
	}
	
	private OrderItem toOrderItem(Map<String,Object> map){
		
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
				
	}

	public Order load(String oid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		loadOrderItem(order);
		return order;
	}
	private void loadOrderItem(Order order) throws SQLException{
		
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
	}

	public void updateStatus(String oid, int status) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_order set status=? where oid=?";
		
		qr.update(sql,status,oid);
	}

	public int findStatus(String oid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select status from t_order where oid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(),oid);
		return number.intValue();
	}

	public PageBean<Order> findAll(int pc) throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList, pc);
	}

	public PageBean<Order> findBystatus(int status, int pc) throws SQLException {
		// TODO Auto-generated method stub
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=",status + ""));
		return findByCriteria(exprList, pc);
	}
	
}
