package com.ssafy.foodfind.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.foodfind.model.dto.Truck;

public interface TruckDao {
	Truck selectMyTruckInfo(String ownerId);
	int selectTruckCountByUser(String ownerId);
	int insert(Truck truck);
	int update(Truck truck);
	List<Truck> selectAllTruck();
	Truck selectTruckByTruckId(String truckId);
	List<Map<String, Object>>selectTrucklocations();
}
