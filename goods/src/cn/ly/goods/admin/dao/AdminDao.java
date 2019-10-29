package cn.ly.goods.admin.dao;

import java.sql.SQLException;

import cn.ly.goods.admin.po.Admin;

public interface AdminDao {
	public Admin find(String adminname,String adminpwd) throws SQLException;
}
