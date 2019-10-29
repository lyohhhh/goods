package cn.ly.goods.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@RequestMapping("/toMain")
	public String toMain() {
		System.out.print("111");
		return "jsps/main";
	}
	
	@RequestMapping("/top")
	public String top() {
		return "jsps/top";
	}
	
	@RequestMapping("/left")
	public String left() {
		return "jsps/left";
	}
	
	@RequestMapping("/search")
	public String search() {
		return "jsps/search";
	}
	
	
	@RequestMapping("/body")
	public String body() {
		return "jsps/body";
	}
	
	@RequestMapping("/adminTop")
	public String adminTop(){
		return "/adminjsps/admin/top";
	}
	
	@RequestMapping("/adminBody")
	public String adminBody(){
		return "/adminjsps/admin/body";
	}
}
