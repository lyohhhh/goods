package cn.ly.goods.cart.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.book.po.Book;
import cn.ly.goods.cart.dao.CartItemDao;
import cn.ly.goods.cart.po.CartItem;
import cn.ly.goods.user.po.User;

@Repository
public class CartItemDaoImpl implements CartItemDao {
	@Autowired
	private QueryRunner qr;

	public List<CartItem> findByUser(String uid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_cartitem c,t_book b where c.bid = b.bid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr.query(sql,new MapListHandler(),uid);
		return toCartItemList(mapList);
	}
	


	public void addCartItem(CartItem cartItem) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_cartitem(cartItemId,quantity,bid,uid) values (?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(),cartItem.getQuantity(),cartItem.getBook().getBid(),cartItem.getUser().getUid()};
		
		qr.update(sql,params);
	}

	public CartItem findByUidAndBid(String uid, String bid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_cartitem where uid=? and bid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),uid,bid);
		CartItem cartItem = toCartItem(map);
		
		return cartItem;
	}

	public void updateQuantity(String cartItemId, int quantity)
			throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql,quantity,cartItemId);
		
	}

	public void batchDelete(String cartItemIds) throws SQLException {
		// TODO Auto-generated method stub
		Object[] cartItemIdsArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdsArray.length);
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql,cartItemIdsArray);
	}
	
	public String toWhereSql(int len){
		StringBuilder sb = new StringBuilder("cartItemId in (");
		for(int i = 0;i<len ;i++){
			sb.append("?");
			if(i < len - 1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}



	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
		// TODO Auto-generated method stub
		Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and " + whereSql;
		
		return toCartItemList(qr.query(sql, new MapListHandler(),cartItemIdArray));
	}



	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_cartItem c, t_book b where c.bid=b.bid and c.cartItemId = ?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),cartItemId);
		return toCartItem(map);
	}
	public List<CartItem> toCartItemList(List<Map<String,Object>> mapList){
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		for( Map<String,Object> map : mapList){
			CartItem cartItem  = toCartItem(map);
			cartItemList.add(cartItem);
		}
		
		return cartItemList;
	}
	
	public CartItem toCartItem(Map<String,Object> map){
		if( map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book  = CommonUtils.toBean(map, Book.class);
		User user  = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		
		return cartItem;
	}
}
