package com.ssafy.foodfind.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.foodfind.model.dto.Truck;

public interface TruckService {
	public Truck selectMyTruckInfo(String ownerId);
	public int selectTruckCountByUser(String ownerId);
	public Boolean insert(Truck truck);
	public void update(Truck truck);
	public List<Truck> selectAllTruck();
}
