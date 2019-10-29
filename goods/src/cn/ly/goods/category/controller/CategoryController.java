package cn.ly.goods.category.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.category.po.Category;
import cn.ly.goods.category.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/findAll")
	public String findAll(Model model)
			throws IOException{
		
		List<Category> parents = categoryService.findAll();
		model.addAttribute("parents",parents);
		
		return "jsps/left";
	}
	
	@RequestMapping("/toBookList")
	public String toBookList(){
		return "jsps/book/list";
	}
}
