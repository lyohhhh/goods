package cn.ly.goods.user.po;

public class User {
	private String uid;
	private String loginname;
	private String loginpass;
	private String email;
	private boolean status;
	private String activationcCode;
	private String reloginpass;
	private String verifyCode;
	private String newpass;
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getActivationcCode() {
		return activationcCode;
	}
	public void setActivationcCode(String activationcCode) {
		this.activationcCode = activationcCode;
	}
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	
	@Override
	public String toString(){
		return "User[uid=" + uid + ",loginname=" + loginname + ",loginpass=" + loginpass
				+ ",email=" + email + ",status=" + status + ",activationcCode=" + activationcCode
				+ ",reloginpass=" + reloginpass + ",verifyCode=" + verifyCode + ",newpass=" + newpass 
				+ "]";
	}
}
