package cn.ly.goods.user.service;


import cn.ly.goods.user.po.User;
import cn.ly.goods.user.service.exception.UserException;

public interface UserService {
	public boolean ajaxValidateLoginname(String loginname);
	
	public boolean ajaxValidateEmail(String email);
	
	public void regist(User user);
	
	public User login(User user);
	
	public void updatePassword(String uid, String newPass, String oldPass) throws UserException;
}
