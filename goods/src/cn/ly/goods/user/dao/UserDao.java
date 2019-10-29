package cn.ly.goods.user.dao;

import java.sql.SQLException;

import cn.ly.goods.user.po.User;

public interface UserDao {
	public boolean ajaxValidateLoginname(String loginname) throws SQLException;//验证用户名
	
	public boolean ajaxValidateEmail(String email) throws SQLException;//验证邮箱
	
	public void add(User user) throws SQLException;//添加用户
	
	public User findByLoginnameAndLoginpass(String loginname,String loginpass) throws SQLException;
	
	public boolean findByUidAndPassword(String uid,String password) throws SQLException;
	
	public void updatePassword(String uid,String password) throws SQLException;
}
