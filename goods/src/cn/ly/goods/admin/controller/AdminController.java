package cn.ly.goods.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.admin.po.Admin;
import cn.ly.goods.admin.service.AdminService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping("/login")
	public String toAdminLogin(){
		return "/adminjsps/login";
	}
	
	@RequestMapping("/loginSubmit")
	public String login(HttpServletRequest req,Admin form,Model model)
		throws IOException{
		
		Admin admin = adminService.login(form);
		if( admin == null){
			model.addAttribute("msg", "用户名或密码错误!");
			return "adminjsps/login";
		}
		req.getSession().setAttribute("admin", admin);
		return "adminjsps/admin/index";
	}
	
	
	@RequestMapping("/toMain")
	public String toMain(){
		return "adminjsps/admin/main";
	}
	
	@RequestMapping("/quit")
	public String quit(HttpServletRequest req,HttpServletResponse resp)
		throws IOException{
		req.getSession().invalidate();
		
		return "redirect:login";
	}
}
