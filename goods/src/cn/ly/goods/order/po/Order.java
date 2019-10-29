package cn.ly.goods.order.po;

import java.util.List;

import cn.ly.goods.user.po.User;

public class Order {
	private String oid;
	private String ordertime;
	private double total;
	private int status;
	private String address;
	private User owner;
	private List<OrderItem> OrderItemList;
	public List<OrderItem> getOrderItemList() {
		return OrderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		OrderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
}
