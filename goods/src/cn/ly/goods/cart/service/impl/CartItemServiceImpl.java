package cn.ly.goods.cart.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.cart.dao.CartItemDao;
import cn.ly.goods.cart.po.CartItem;
import cn.ly.goods.cart.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	@Autowired
	private CartItemDao cartItemDao;

	public List<CartItem> myCart(String uid) {
		// TODO Auto-generated method stub
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void add(CartItem cartItem) {
		// TODO Auto-generated method stub
		try {
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(),cartItem.getBook().getBid());
			if(_cartItem == null){
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			} else {
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

	public CartItem updateQuantity(String cartItemId, int quantity) {
		// TODO Auto-generated method stub
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

	public void batchDelete(String cartItemIds) {
		// TODO Auto-generated method stub
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public List<CartItem> loadCartItems(String cartItemIds) {
		// TODO Auto-generated method stub
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
