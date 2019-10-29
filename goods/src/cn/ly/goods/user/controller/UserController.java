package cn.ly.goods.user.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.user.po.User;
import cn.ly.goods.user.service.UserService;
import cn.ly.goods.user.service.exception.UserException;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/toRegist")
	public String toRegist(){
		return "jsps/user/regist";
	}
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "jsps/user/login";
	}
	@RequestMapping("verifyCode")
	public void verifyCode(HttpServletRequest req,HttpServletResponse res)
	throws IOException{
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		
		VerifyCode.output(image, res.getOutputStream());
		req.getSession().setAttribute("vCode", vc.getText());
	}
	
	
	@RequestMapping("/ajaxValidateLoginname")
	public String ajaxValidateLoginname (HttpServletResponse resp,String loginname)
			throws IOException{
		
		boolean b = userService.ajaxValidateLoginname(loginname);
		resp.getWriter().print(b);
		
		return null;
	}
	
	@RequestMapping("/ajaxValidateEmail")
	public String ajaxValidateEmail (HttpServletResponse resp,String email)
			throws IOException{
		
		boolean b = userService.ajaxValidateEmail(email);
		resp.getWriter().print(b);
		return null;
	}
	
	
	@RequestMapping("/ajaxValidateVerifyCode")
	public String ajaxValidateVerifyCode(HttpServletRequest req,HttpServletResponse res,String verifyCode)
			throws IOException{
		String vcode = (String)req.getSession().getAttribute("vCode");
		
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		res.getWriter().print(b);
		return null;
	}
	
	
	@RequestMapping("/regist")
	public String regist(HttpServletRequest req, User formUser,Model model)
		throws IOException{
		Map<String,String> errors = validateRegist(formUser, req.getSession());
		
		if(errors.size() > 0){
			model.addAttribute("form",formUser);
			model.addAttribute("errors",errors);
			return "/jsp/user/regist";
		}
		
		userService.regist(formUser);
		
		model.addAttribute("code","success");
		model.addAttribute("msg","注册功能，请马上到邮箱激活");
		return "/jsps/msg";
	}
	
	
	private Map<String,String> validateRegist(User formUser,HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		
		String loginname = formUser.getLoginname();
		
		if(loginname == null || loginname.trim().isEmpty()){
			errors.put("loginname", "用户名不能为空");
			
		}else if (loginname.length() <3 ||loginname.length() >20 ){
			errors.put("loginname", "用户名在3~20位之间");
		}else if (!userService.ajaxValidateLoginname(loginname)){
			errors.put("loginname", "用户名已经被注册");
		}
		
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()){
			errors.put("loginpass", "密码不能为空");
		}else if (loginpass.length() <3 || loginpass.length() >20){
			errors.put("loginname", "密码在3~20位之间");
		}
		
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()){
			errors.put("reloginpass", "确认密码不能为空");
		}else if (!reloginpass.equals(loginpass)){
			errors.put("reloginpass", "两次密码不一样");
		}
		
		String email = formUser.getEmail();
		
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空");
		}else if (email.matches("^([a-zA-Z0-9_-]+@[a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("email", "邮箱格式错误");
		}else if (!userService.ajaxValidateEmail(email)){
			errors.put("email", "邮箱已被注册");
		}
		
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()){
			errors.put("verifyCode", "验证码不能为空");
		}else if (!verifyCode.equalsIgnoreCase(vcode)){
			errors.put("verifyCode", "验证码格式错误");

		}
		return errors;
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse resp,User formUser,Model model)
		throws IOException{
			Map<String,String> errors = validatelogin(formUser,req.getSession());
			if(errors.size() > 0 ){
				model.addAttribute("form",formUser);
				model.addAttribute("errors",errors);
				return "jsps/user/login";
			}
			
			User user = userService.login(formUser);
			
			if(user == null){
				model.addAttribute("msg","用户名或密码错误");
				model.addAttribute("user",formUser);
				return "jsps/user/login";
			}else{
				if( !user.isStatus() ){
					model.addAttribute("msg","你还没有激活");
					model.addAttribute("user",formUser);
					return "jsps/user/login";
				}else{
					req.getSession().setAttribute("sessionUser", user);
					String loginname = user.getLoginname();
					loginname = URLEncoder.encode(loginname,"utf-8");
					Cookie cookie = new Cookie("loginname",loginname);
					cookie.setMaxAge(60 * 60 * 24 * 10);
					resp.addCookie(cookie);
					return "redirect:/index.jsp";
				}
			}
	}
	private Map<String,String> validatelogin(User formUser, HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		return errors;
	}
	
	@RequestMapping("/quit")
	public String quit(HttpServletRequest req)
			throws IOException{
			req.getSession().invalidate();
			return "redirect:toLogin";
	}
	
	@RequestMapping("/toPwd")
	public String toPwd(){
		return "jsps/user/pwd";
	}
	
	@RequestMapping("/updatePassword")
	public String updatePassword(HttpServletRequest req, User formUser,Model model)
			throws IOException{
			User user = (User)req.getSession().getAttribute("sessionUser");
			if(user == null){
				model.addAttribute("msg","您还没有登录");
				return "redirect:toLogin";
			}
			try {
				userService.updatePassword(user.getUid(), formUser.getNewpass(), formUser.getLoginpass());
				model.addAttribute("msg","修改成功");
				model.addAttribute("code","success");
				return "jsps/msg";
			} catch (UserException e) {
				// TODO Auto-generated catch block
				model.addAttribute("msg",e.getMessage());
				model.addAttribute("user",formUser);
				return "jsps/user/pwd";
			}
			
	}
}
