package cn.ly.goods.admin.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ly.goods.admin.dao.AdminDao;
import cn.ly.goods.admin.po.Admin;
import cn.ly.goods.admin.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	public Admin login(Admin admin) {
		// TODO Auto-generated method stub
		try {
			return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
}
