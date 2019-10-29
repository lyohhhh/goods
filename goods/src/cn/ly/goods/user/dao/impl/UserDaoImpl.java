package cn.ly.goods.user.dao.impl;

import java.sql.SQLException;


import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.jdbc.TxQueryRunner;
import cn.ly.goods.user.dao.UserDao;
import cn.ly.goods.user.po.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private TxQueryRunner qr;

	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}

	public boolean ajaxValidateEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}

	public void add(User user) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_user values (?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getLoginname(),user.getLoginpass(),
				user.getEmail(),user.isStatus(),user.getActivationcCode()};
		qr.update(sql,params);
		
	}

	public User findByLoginnameAndLoginpass(String loginname, String loginpass)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_user where loginname=? and loginpass=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),loginname,loginpass);
	}

	public boolean findByUidAndPassword(String uid, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(),uid,password);
		return number.intValue()>0;
	}

	public void updatePassword(String uid, String password) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql,password,uid);
	}
}
