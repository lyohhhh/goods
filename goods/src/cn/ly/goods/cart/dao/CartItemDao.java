package cn.ly.goods.cart.dao;

import java.sql.SQLException;
import java.util.List;

import cn.ly.goods.cart.po.CartItem;

public interface CartItemDao {
	
	public List<CartItem> findByUser(String uid) throws SQLException;
	
	public void addCartItem(CartItem cartItem) throws SQLException;
	
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException;
	
	public void updateQuantity(String cartItemId,int quantity) throws SQLException;
	
	public void batchDelete(String cartItemIds) throws SQLException;
	
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException;

	public CartItem findByCartItemId(String cartItemId) throws SQLException;
}
