package cn.ly.goods.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.cart.po.CartItem;
import cn.ly.goods.cart.service.CartItemService;
import cn.ly.goods.order.po.Order;
import cn.ly.goods.order.po.OrderItem;
import cn.ly.goods.order.service.OrderService;
import cn.ly.goods.pager.PageBean;
import cn.ly.goods.user.po.User;


@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartItemService cartItemService;
	
	
	@RequestMapping("/createOrder")
	public String createOrder(HttpServletRequest req,Model model)
		throws IOException{
		String cartItemIds = req.getParameter("cartItemIds");
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		if( cartItemList.size() == 0 ){
			model.addAttribute("code","error");
			model.addAttribute("msg", "您没有购买的图书，无法下单!");
			return "jsps/msg";
		}
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(String.format("%tF%<tT", new Date()));
		order.setStatus(1);
		order.setAddress(req.getParameter("adress"));
		User owner = (User)req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);
		
		BigDecimal total = new BigDecimal("0");
		for( CartItem cartItem : cartItemList){
			total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
		}
		
		order.setTotal(total.doubleValue());
		
		
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for( CartItem cartItem : cartItemList){
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		
		order.setOrderItemList(orderItemList);
		
		orderService.createOrder(order);
		
		cartItemService.batchDelete(cartItemIds);
		
		model.addAttribute("order", order);
		
		return "jsps/order/ordersucc";
	}
	
	
	@RequestMapping("/myOrders")
	public String myOrders(HttpServletRequest req,Model model)
		throws IOException{
		int pc = getPc(req);
		String url = getUrl(req);
		
		User user = (User) req.getSession().getAttribute("sessionUser");
		
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		
		pb.setUrl(url);
		model.addAttribute("pb", pb);
		return "jsps/order/list";
	}
	
	
	private int getPc(HttpServletRequest req){
		
		int pc = 1;
		String param = req.getParameter("pc");
		
		if( param != null && !param.trim().isEmpty()){
			try{
				pc  = Integer.parseInt(param);
			}catch(RuntimeException e){}	
		}
		return pc;
	}
	
	
	private String getUrl(HttpServletRequest req){
		
		String url = req.getRequestURI() + "?" + req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		
		if( index != -1){
			url = url.substring(0,index);
		}
		return url;
	}
	
	
	@RequestMapping("/load")
	public String load(String oid,String btn,Model model)
			throws IOException{
		Order order = orderService.load(oid);
		
		model.addAttribute("order",order);
		
		model.addAttribute("btn",btn);
		return "jsps/order/desc";
	}
	
	
	@RequestMapping("/cancel")
	public String cancel(String oid,Model model)
		throws IOException{
		
		int status = orderService.findStatus(oid);
		if(status != 1){
			model.addAttribute("code","error");
			model.addAttribute("msg","状态不对，无法取消");
			return "f:/jsps/msg.jsp";
		}
		
		orderService.updateStatus(oid, 5);
		model.addAttribute("code","success");
		model.addAttribute("msg","您的订单已经取消");
		return "jsps/msg";
	}
	
	@RequestMapping("/confirm")
	public String confirm(String oid,Model model)
			throws IOException{
		int status = orderService.findStatus(oid);
		if(status != 3){
			model.addAttribute("code","error");
			model.addAttribute("msg","状态不对，不能确认收货");
			return "jsps/msg";
		}
		
		orderService.updateStatus(oid, 4);
		model.addAttribute("code","success");
		model.addAttribute("msg","交易成功");
		return "jsps/msg";
		
	}
	
	
	@RequestMapping("/paymentPre")
	public String paymentPre(String oid,Model model)
		throws IOException,ServletException{
		model.addAttribute("order",orderService.load(oid));
		return "jsps/order/pay";
	}

}
