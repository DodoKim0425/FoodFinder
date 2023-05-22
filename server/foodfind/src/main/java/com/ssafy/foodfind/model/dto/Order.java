package com.ssafy.foodfind.model.dto;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

public class Order {
	private int orderId;
	private int userId;
	private int truckId;
	private String description;
	private BigDecimal totalPrice;
	private Date orderTime;
	private String orderStatus;
	
	private List<OrderItem> items;
	
	public Order() {
		super();
	}
	public Order(int orderId, int userId, int truckId, String description, BigDecimal totalPrice, Date orderTime,
			String orderStatus) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.truckId = truckId;
		this.description = description;
		this.totalPrice = totalPrice;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
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
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
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
	
	
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + userId + ", truckId=" + truckId + ", description="
				+ description + ", totalPrice=" + totalPrice + ", orderTime=" + orderTime + ", orderStatus="
				+ orderStatus + ", items=" + items + "]";
	}
	
}
