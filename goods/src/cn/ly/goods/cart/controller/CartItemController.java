package cn.ly.goods.cart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.book.po.Book;
import cn.ly.goods.cart.po.CartItem;
import cn.ly.goods.cart.service.CartItemService;
import cn.ly.goods.user.po.User;


@Controller
@RequestMapping("/cart")
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;
	
	@RequestMapping("/myCart")
	public String myCart(HttpServletRequest req,HttpServletResponse resp)
			throws IOException{
		
		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		req.setAttribute("cartItemList", cartItemList);
		
		return "jsps/cart/list";
	}
	
	
	@RequestMapping("/add")
	public String add(HttpServletRequest req,HttpServletResponse resp,CartItem cartItem,Book book)
			throws IOException{
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		
		cartItemService.add(cartItem);
		return myCart(req, resp);
	}
	
	
	@RequestMapping("/batchDelete")
	public String batchDelete(HttpServletRequest req,HttpServletResponse resp,String cartItemIds)
			throws IOException{
		cartItemService.batchDelete(cartItemIds);
		return myCart(req,resp);
	}
	
	
	@RequestMapping("/updateQuantity")
	public String updateQuantity(HttpServletResponse resp,String cartItemId,int quantity)
		throws IOException{
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
	
		resp.getWriter().print(sb);
		
		return null;
	}
	
	
	@RequestMapping("/loadCartItems")
	public String loadCartItems(HttpServletRequest req,String cartItemIds,double total,Model model)
			throws IOException{
		
		System.out.print("-----------");
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		
		model.addAttribute("cartItemList",cartItemList);
		model.addAttribute("total",total);
		model.addAttribute("cartItemIds",cartItemIds);
		
		return "jsps/cart/showitem";
		
		
	}
}
