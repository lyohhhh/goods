package cn.ly.goods.admin.categoty.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.book.service.BookService;
import cn.ly.goods.category.po.Category;
import cn.ly.goods.category.service.CategoryService;


@Controller
@RequestMapping("/adminCategory")
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping("/findAll")
	public String findAll(HttpServletRequest req,HttpServletResponse resp)
	 	throws IOException{
		req.setAttribute("parents", categoryService.findAll());
		return "adminjsps/admin/category/list";
	}
	
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "adminjsps/admin/category/add";
	}
	
	@RequestMapping("/addParent")
	public String addParent(HttpServletRequest req,HttpServletResponse resp,Category parent)
			throws ServletException,IOException{
		parent.setCid(CommonUtils.uuid());
		categoryService.add(parent);
		return findAll(req, resp);
	}
	
	@RequestMapping("/addChildPre")
	public String addChildPre(String pid ,Model model)
		throws IOException{
		List<Category> parents = categoryService.findParents();
		
		model.addAttribute("pid",pid);
		model.addAttribute("parents",parents);
		return "adminjsps/admin/category/add2";
	}
	
	
	
	@RequestMapping("/addChild")
	public String addChild(HttpServletRequest req,HttpServletResponse resp,Category child,String pid)
				throws IOException{
		child.setCid(CommonUtils.uuid());
		
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		return findAll(req,resp);
	}
	
	@RequestMapping("/editParentPre")
	public String editParentPre(String cid,Model model)
			throws IOException{
		Category parent = categoryService.load(cid);
		model.addAttribute("parent",parent);
		return "adminjsps/admin/category/edit";
	}
	
	@RequestMapping("/editParent")
	public String editParent(HttpServletRequest req, HttpServletResponse resp,Category parent)
			throws IOException{
		categoryService.edit(parent);
		return findAll(req, resp);
		
	}
	
	@RequestMapping("/editChildPre")
	public String editChildPre(String cid ,Model model)
			throws IOException{
		Category child = categoryService.load(cid);
		model.addAttribute("child",child);
		model.addAttribute("parents",categoryService.findParents());
		
		return "adminjsps/admin/category/edit2";
	}
	
	@RequestMapping("/editChild")
	public String editChild(HttpServletRequest req, HttpServletResponse resp,Category child)
			throws IOException{
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.edit(child);
		return findAll(req, resp);
		
	}
	
	@RequestMapping("/deleteParent")
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp,String cid,Model model)
			throws IOException{
		
		int cnt = categoryService.findChildCountByParent(cid);
		
		if( cnt > 0){
			
			model.addAttribute("msg","该分类下还有子目录，不能删除");
			return "adminjsps/msg";
		}else {
			categoryService.delete(cid);
			return findAll(req, resp);
		}
		
	}
	@RequestMapping("/deleteChild")
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp,String cid,Model model)
			throws IOException{
		
		int cnt = bookService.findBookCountByCategory(cid);
		
		if( cnt > 0){
			
			model.addAttribute("msg","该分类下还有图书，不能删除");
			return "adminjsps/msg";
		}else {
			categoryService.delete(cid);
			return findAll(req, resp);
		}
		
	}
}
