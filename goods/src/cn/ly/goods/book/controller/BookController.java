package cn.ly.goods.book.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ly.goods.book.po.Book;
import cn.ly.goods.book.service.BookService;
import cn.ly.goods.pager.PageBean;


@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/findByCategory")
	public String findByCategory(HttpServletRequest req,HttpServletResponse resp,
			String cid,Model model) throws IOException{
			int pc = getPc(req);
			String url = getUrl(req); 
			PageBean<Book> pb = bookService.findByCategory(cid, pc);
			pb.setUrl(url);
			model.addAttribute("pb",pb);
			System.out.println(pb.getBeanList());
			return "jsps/book/list";
	}
	
	
	@RequestMapping("/toGj")
	public String toGj(){
		return "jsps/gj";
	}
	
	@RequestMapping("/findByBname")
	public String findByBname(HttpServletRequest req,HttpServletResponse resp,String bname,Model model)
		throws IOException{
		int pc = getPc(req);
		String url = getUrl(req);
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		pb.setUrl(url);
		model.addAttribute("pb",pb);
		return "jsps/book/list";
	}
	
	@RequestMapping("/findByAuthor")
	public String findByAuthor(HttpServletRequest req,HttpServletResponse resp,String author,Model model)
			throws IOException{
			int pc = getPc(req);
			String url = getUrl(req);
			PageBean<Book> pb = bookService.findByAuthor(author, pc);
			pb.setUrl(url);
			model.addAttribute("pb",pb);
			return "jsps/book/list";
	}
	
	
	@RequestMapping("/findByPress")
	public String findByPress(HttpServletRequest req,HttpServletResponse resp,String press,Model model)
			throws IOException{
			int pc = getPc(req);
			String url = getUrl(req);
			PageBean<Book> pb = bookService.findByPress(press, pc);
			pb.setUrl(url);
			model.addAttribute("pb",pb);
			return "jsps/book/list";
	}
	
	
	@RequestMapping("/findByCombination")
	public String findByPress(HttpServletRequest req,HttpServletResponse resp,Book criteria,Model model)
			throws IOException{
			int pc = getPc(req);
			String url = getUrl(req);
			PageBean<Book> pb = bookService.findByCombination(criteria, pc);
			pb.setUrl(url);
			model.addAttribute("pb",pb);
			return "jsps/book/list";
	}
	
	
	@RequestMapping("/load")
	public String load(String bid,Model model)
		throws ServletException,IOException{
		Book book = bookService.load(bid);
		model.addAttribute("book",book);
		return "jsps/book/desc";
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
	
	
	public String getUrl(HttpServletRequest req){
		String url = req.getRequestURI() + "?" + req.getQueryString();
		
		int index = url.lastIndexOf("&pc=");
		
		if(index != -1){
			url = url.substring(0,index);
		}
		return url; 
	}
	
}


