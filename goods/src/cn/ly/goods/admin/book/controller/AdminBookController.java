package cn.ly.goods.admin.book.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.commons.CommonUtils;
import cn.ly.goods.book.po.Book;
import cn.ly.goods.book.service.BookService;
import cn.ly.goods.category.po.Category;
import cn.ly.goods.category.service.CategoryService;
import cn.ly.goods.pager.PageBean;

@Controller
@RequestMapping("/adminBook")
public class AdminBookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping("/toMain")
	public String toMain(){
		return "adminjsps/admin/book/main";
	}
	
	@RequestMapping("/findCategoryAll")
	public String findCategoryAll(Model model)	
			throws IOException{
		
		List<Category> parents = categoryService.findAll();
		model.addAttribute("parents",parents);
		return "adminjsps/admin/book/left";
	}
	
	@RequestMapping("/toBody")
	public String toBody(){
		return "adminjsps/admin/book/body";
	}
	
	@RequestMapping("/findByCategory")
	public String findByCategory(HttpServletRequest req,String cid,Model model)
			throws IOException{
		int pc = getPc(req);
		
		String url = getUrl(req);
		
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		pb.setUrl(url);
		
		model.addAttribute("pb",pb);
		return "adminjsps/admin/book/list";
	}
	
	@RequestMapping("/findByAuthor")
	public String findByAuthor(HttpServletRequest req, String author,Model model)
			throws IOException{
		int pc = getPc(req);
		
		String url = getUrl(req);
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		pb.setUrl(url);
		
		model.addAttribute("pb",pb);
		return "adminjsps/admin/book/list";
	}
	
	
	@RequestMapping("/findByPress")
	public String findByPress(HttpServletRequest req, String press,Model model)
			throws IOException{
		int pc = getPc(req);
		
		String url = getUrl(req);
		PageBean<Book> pb = bookService.findByPress(press, pc);
		pb.setUrl(url);
		
		req.setAttribute("pb", pb);
		model.addAttribute("pb",pb);
		return "adminjsps/admin/book/list";
	}
	
	
	@RequestMapping("/toGj")
	public String toGj(){
		return "adminjsps/admin/book/gj";
	}
	

	@RequestMapping("/findByCombination")
	public String findByCombination(HttpServletRequest req, Book criteria,Model model)
			throws IOException{
		
		int pc = getPc(req);
		
		String url = getUrl(req);
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		pb.setUrl(url);
		
//		req.setAttribute("pb", pb);
		model.addAttribute("pb",pb);
		return "adminjsps/admin/book/list";
	}
	
	@RequestMapping("/load")
	public String load(String bid,Model model)
			throws ServletException , IOException{
		
		Book book = bookService.load(bid);
		
		model.addAttribute("book",book);
		
		model.addAttribute("parents",categoryService.findParents());
		String pid = book.getCategory().getParent().getCid();
		
		model.addAttribute("children",categoryService.findParents());
		
		return "adminjsps/admin/book/desc";
	}
	
	
	@RequestMapping("/ajaxFindChildren")
	public String ajaxFindChildren(HttpServletResponse resp,String pid)
			throws IOException{
		
		List<Category> children = categoryService.findParents();
		String json = toJson(children);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(json);
		return null;
	}
	
	
	private String toJson(Category category){
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	
	}
	
	
	@RequestMapping("/edit")
	public String edit(Book book,Category category,Model model)
			throws IOException{
		
		book.setCategory(category);
		bookService.edit(book);
		model.addAttribute("msg","修改成功！");
		return "adminjsps/msg";
	}
	
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req,String bid,Model model)
			throws IOException{
		
		Book book = bookService.load(bid);
		String savepath = req.getSession().getServletContext().getRealPath("/");
		new File(savepath ,book.getImage_b()).delete();
		new File(savepath ,book.getImage_w()).delete();
		
		bookService.delete(bid);
		model.addAttribute("msg","图书删除成功！");
		return "adminjsps/msg";
	}
	
	
	@RequestMapping("/addPre")
	public String addPre(Model model)
			throws IOException{
		List<Category> parents = categoryService.findParents();
		model.addAttribute("parents",parents);
		return "adminjsps/admin/book/add";
	}
	
	
	@RequestMapping("/add")
	public String add(HttpServletRequest req,HttpServletResponse resp)
			throws ServletException ,IOException{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		
		sfu.setFileSizeMax(80 * 1024);
		List<FileItem> fileItemList=  null;
		
		try {
			fileItemList = sfu.parseRequest(req);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			error("上传的文件超过了80kb",req,resp);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		for( FileItem fileItem : fileItemList){
			if( fileItem.isFormField()){
				map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
			}
		}
		
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		
		book.setCategory(category);
		
		FileItem fileItem = fileItemList.get(1);
		String filename = fileItem.getName();
		int index = filename.lastIndexOf("\\");
		if( index != -1){
			filename = filename.substring(index + 1);
		}
		
		filename = CommonUtils.uuid() + "_" + filename;
		if( !filename.toLowerCase().endsWith(".jpg")){
			error("上传的图片拓展名必须是.jpg",req,resp);
		}
		
		String savepath = req.getSession().getServletContext().getRealPath("/book_img");
		
		File destFile = new File(savepath,filename);
		try{
			fileItem.write(destFile);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		Image image = icon.getImage();
		
		if( image.getWidth(null) >350 || image.getHeight(null) >350 ){
			error("您上传的图片超出了尺寸350*350",req,resp);
			destFile.delete();
		}
		
		book.setImage_w("book_img/" + filename );
		
		fileItem = fileItemList.get(2);
		filename = fileItem.getName();
		
		index = filename.lastIndexOf("\\");
		if( index != -1){
			filename = filename.substring(index + 1);
			
		}
		
		filename = CommonUtils.uuid() + "_" + filename;
		if( !filename.toLowerCase().endsWith(".jpg")){
			error("上传的图片名后缀必须是.jpg",req,resp);
			
		}
		savepath = req.getSession().getServletContext().getRealPath("/book_img");
		destFile = new File(savepath,filename);
		try{
			fileItem.write(destFile);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		icon = new ImageIcon(destFile.getAbsolutePath());
		image = icon.getImage();
		if( image.getWidth(null) >350 || image.getHeight(null) >350 ){
			error("您上传的图片超出了尺寸350*350",req,resp);
			destFile.delete();
		}
		book.setImage_b("book_img/" + filename );
		book.setBid(CommonUtils.uuid());
		bookService.add(book);
		req.setAttribute("msg", "图片上传成功！");
		
		return "adminjsps/msg";
		
		
	}
	
	
	private String error(String msg,HttpServletRequest req,HttpServletResponse resp)
			throws ServletException,IOException{
		req.setAttribute("msg", msg);
		
		req.setAttribute("parents", categoryService.findParents());
		
		return "adminjsps/admin/book/add";
	}
	private String toJson(List<Category> categoryList){
		
		StringBuilder sb = new StringBuilder("[");
		for( int i = 0; i<categoryList.size(); i++){
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
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

