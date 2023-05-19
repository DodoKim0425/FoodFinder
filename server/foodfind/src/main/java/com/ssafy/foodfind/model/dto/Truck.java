package com.ssafy.foodfind.model.dto;

public class Truck {
	private int truckId;
	private int ownerId;
	private String name;
	private String description;
	private String location;
	private String currentStatus;
	private float rating;
	
	public Truck() {
		super();
	}
	
	public Truck(int truckId, int ownerId, String name, String description, String location, String currentStatus,
			float rating) {
		super();
		this.truckId = truckId;
		this.ownerId = ownerId;
		this.name = name;
		this.description = description;
		this.location = location;
		this.currentStatus = currentStatus;
		this.rating = rating;
	}
	public int getTruckId() {
		return truckId;
	}
	public void setTruckId(int truckId) {
		this.truckId = truckId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Truck [truckId=" + truckId + ", ownerId=" + ownerId + ", name=" + name + ", description=" + description
				+ ", location=" + location + ", currentStatus=" + currentStatus + ", rating=" + rating + "]";
	}
	
	
}
