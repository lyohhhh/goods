package cn.ly.goods.user.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.ly.goods.user.dao.UserDao;
import cn.ly.goods.user.po.User;
import cn.ly.goods.user.service.UserService;
import cn.ly.goods.user.service.exception.UserException;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void regist(User user) {
		// TODO Auto-generated method stub
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationcCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		try {
			userDao.add(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		Properties prop = new Properties();
		
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e1);
		}
		String host = prop.getProperty("host");
		String name = prop.getProperty("username");
		String pass = prop.getProperty("password");
		Session session = MailUtils.createSession(host, name, pass);
		
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationcCode());
		
		Mail mail = new Mail(from, to, subject, content);
		
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public User login(User user) {
		// TODO Auto-generated method stub
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public void updatePassword(String uid, String newPass, String oldPass)
			throws UserException {
		// TODO Auto-generated method stub
		try {
			boolean bool = userDao.findByUidAndPassword(uid, oldPass);
			if(!bool){
				throw new UserException("旧密码错误  ");
			}
			userDao.updatePassword(uid, newPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
