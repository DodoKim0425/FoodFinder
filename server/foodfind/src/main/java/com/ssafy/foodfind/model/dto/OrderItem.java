package com.ssafy.foodfind.model.dto;

public class OrderItem {
	private int orderItemId;
	private int orderId;
	private int itemId;
	private int quantity;
	
	
	public OrderItem() {
		super();
	}
	
	public OrderItem(int orderItemId, int orderId, int itemId, int quantity) {
		super();
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", orderId=" + orderId + ", itemId=" + itemId + ", quantity="
				+ quantity + "]";
	}
	
	
}
