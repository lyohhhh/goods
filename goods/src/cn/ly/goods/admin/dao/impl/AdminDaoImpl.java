package cn.ly.goods.admin.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.ly.goods.admin.dao.AdminDao;
import cn.ly.goods.admin.po.Admin;


@Repository
public class AdminDaoImpl implements AdminDao {
	@Autowired
	private QueryRunner qr;

	public Admin find(String adminname, String adminpwd) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_admin where adminname=? and adminpwd=?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class),adminname,adminpwd);
	}
}
