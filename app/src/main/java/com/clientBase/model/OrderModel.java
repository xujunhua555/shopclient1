package com.clientBase.model;

import java.io.Serializable;

public class OrderModel implements Serializable {

	private String orderMessageMoney;
	private String orderMessageId;
	private String orderAddress;
	private String orderUserId;
	private String orderUserName;
	private String orderCreatime;
	private String orderId;
	private String orderMessageName;
	private ShopModel shopMessage;

	public String getOrderMessageMoney() {
		return orderMessageMoney;
	}

	public void setOrderMessageMoney(String orderMessageMoney) {
		this.orderMessageMoney = orderMessageMoney;
	}

	public String getOrderMessageId() {
		return orderMessageId;
	}

	public void setOrderMessageId(String orderMessageId) {
		this.orderMessageId = orderMessageId;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getOrderUserName() {
		return orderUserName;
	}

	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}

	public String getOrderCreatime() {
		return orderCreatime;
	}

	public void setOrderCreatime(String orderCreatime) {
		this.orderCreatime = orderCreatime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderMessageName() {
		return orderMessageName;
	}

	public void setOrderMessageName(String orderMessageName) {
		this.orderMessageName = orderMessageName;
	}

	public ShopModel getShopMessage() {
		return shopMessage;
	}

	public void setShopMessage(ShopModel shopMessage) {
		this.shopMessage = shopMessage;
	}

}
