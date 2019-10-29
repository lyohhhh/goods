package cn.ly.goods.admin.order.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.book.po.Book;
import cn.ly.goods.order.po.Order;
import cn.ly.goods.order.service.OrderService;
import cn.ly.goods.pager.PageBean;

@Controller
@RequestMapping("/adminOrder")
public class AdminOrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/findAll")
	public String findAll(HttpServletRequest req,Model model)
		throws IOException{
		int pc = getPc(req);
		
		String url = getUrl(req);
		PageBean<Order> pb = orderService.findAll(pc);
		pb.setUrl(url);
		
		model.addAttribute("pb",pb);
		return "adminjsps/admin/order/list";
	}
	
	
	@RequestMapping("/findByStatus")
	public String findByStatus(HttpServletRequest req,Model model)
			throws IOException{
			int pc = getPc(req);
			
			String url = getUrl(req);
			int status = Integer.parseInt(req.getParameter("status"));
			PageBean<Order> pb = orderService.findByStatus(status, pc);
			pb.setUrl(url);
			
			model.addAttribute("pb",pb);
			return "adminjsps/admin/order/list";
		}
	
	
	@RequestMapping("/load")
	public String load(String oid,String btn,Model model)
			throws IOException{
		Order order = orderService.load(oid);
		
		model.addAttribute("order",order);
		model.addAttribute("btn",btn);
		return "adminjsps/admin/order/desc";
	}
	
	
	@RequestMapping("/cancel")
	public String cancel(String oid,Model model)
			throws IOException{
		int status = orderService.findStatus(oid);
		if(status != 1){
			model.addAttribute("code","error");
			model.addAttribute("msg","状态不对，无法取消");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);
		model.addAttribute("code","success");
		model.addAttribute("msg","您的订单已经取消");
		return "adminjsps/msg";
		
	}
	
	
	@RequestMapping("/deliver")
	public String deliver(String oid,Model model)
			throws IOException{
		int status = orderService.findStatus(oid);
		if(status != 2){
			model.addAttribute("code","error");
			model.addAttribute("msg","状态不对，无法发货");
			return "adminjsps/msg";
		}
		orderService.updateStatus(oid, 3);
		model.addAttribute("code","success");
		model.addAttribute("msg","您的订单已经发货");
		return "adminjsps/msg";
		
	}
	
	public String getUrl(HttpServletRequest req){
		String url = req.getRequestURI() + "?" + req.getQueryString();
		
		int index = url.lastIndexOf("&pc=");
		
		if(index != -1){
			url = url.substring(0,index);
		}
		return url; 
	}
	public int getPc(HttpServletRequest req){
		int pc = 1;
		String param = req.getParameter("pc");
		
		if(param != null && !param.trim().isEmpty()){
			try{
				pc = Integer.parseInt(param);
				}catch(RuntimeException e){}
		}
		return pc;
	}
	
}
