package com.ssafy.foodfind.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.foodfind.model.dto.Truck;

public interface TruckService {
	public Truck selectMyTruckInfo(String ownerId);
	public int selectTruckCountByUser(String ownerId);
	public Boolean insert(Truck truck);
	public void update(Truck truck);
	public List<Truck> selectAllTruck();
	public List<Truck>selectTruckByTruckId(String truckId);
	public List<Map<String, Object>>selectTrucklocations();
}
