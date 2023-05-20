package com.ssafy.foodfind.model.dto;

import java.math.BigDecimal;

public class FoodItem {
	private int itemId;
	private int truckId;
	private String name;
	private String description;
	private BigDecimal price;
	private String status;
	private boolean usingYN;
	
	public FoodItem() {
		super();
	}
	

	public FoodItem(int itemId, int truckId, String name, String description, BigDecimal price, String status,
			boolean usingYN) {
		super();
		this.itemId = itemId;
		this.truckId = truckId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
		this.usingYN = usingYN;
	}


	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getTruckId() {
		return truckId;
	}
	public void setTruckId(int truckId) {
		this.truckId = truckId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public boolean isUsingYN() {
		return usingYN;
	}


	public void setUsingYN(boolean usingYN) {
		this.usingYN = usingYN;
	}


	@Override
	public String toString() {
		return "FoodItem [itemId=" + itemId + ", truckId=" + truckId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", status=" + status + ", usingYN=" + usingYN + "]";
	}


	
	
	
}
