package com.ssafy.foodfind.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class UserOrderItemDetail {
	private int orderId;
	private int userId;
	private int truckId;
	private String description;
	private int totalPrice;
	private Date orderTime;
	private String orderStatus;
	private int orderItemId;
	private int itemId;
	private int quantity;
	private String truckName;
	private String customerName;
	private String itemName;
	
	public UserOrderItemDetail() {
		super();
	}
	
	public UserOrderItemDetail(int orderId, int userId, int truckId, String description, int totalPrice,
			Date orderTime, String orderStatus, int orderItemId, int itemId, int quantity, String truckName,
			String customerName, String itemName) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.truckId = truckId;
		this.description = description;
		this.totalPrice = totalPrice;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.orderItemId = orderItemId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.truckName = truckName;
		this.customerName = customerName;
		this.itemName = itemName;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTruckId() {
		return truckId;
	}
	public void setTruckId(int truckId) {
		this.truckId = truckId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getTruckName() {
		return truckName;
	}
	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Override
	public String toString() {
		return "OrderItemDetail [orderId=" + orderId + ", userId=" + userId + ", truckId=" + truckId + ", description="
				+ description + ", totalPrice=" + totalPrice + ", orderTime=" + orderTime + ", orderStatus="
				+ orderStatus + ", orderItemId=" + orderItemId + ", itemId=" + itemId + ", quantity=" + quantity
				+ ", truckName=" + truckName + ", customerName=" + customerName + ", itemName=" + itemName + "]";
	}
	
}
