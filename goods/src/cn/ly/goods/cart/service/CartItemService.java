package cn.ly.goods.cart.service;

import java.util.List;

import cn.ly.goods.cart.po.CartItem;

public interface CartItemService {

	public List<CartItem> myCart(String uid);
	
	public void add(CartItem cartItem);
	
	public CartItem updateQuantity(String cartItemId,int quantity);
	
	public void batchDelete(String cartItemIds);
	
	public List<CartItem> loadCartItems(String cartItemIds);
}
