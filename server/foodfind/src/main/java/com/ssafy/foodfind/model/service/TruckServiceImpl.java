package com.ssafy.foodfind.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.foodfind.model.dao.TruckDao;
import com.ssafy.foodfind.model.dao.UserDao;
import com.ssafy.foodfind.model.dto.Truck;

@Service
public class TruckServiceImpl implements TruckService {


	@Autowired
	private TruckDao truckDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Truck selectMyTruckInfo(String ownerId) {
		return truckDao.selectMyTruckInfo(ownerId);
	}
	
	@Override
	public int selectTruckCountByUser(String ownerId) {
		return truckDao.selectTruckCountByUser(ownerId);
	}
	
	@Override
	@Transactional
	public Boolean insert(Truck truck){
		truckDao.insert(truck);
		userDao.updateUserToOwner(Integer.toString(truck.getOwnerId()));
		return true;
	}

	@Override
	@Transactional
	public void update(Truck truck) {
		truckDao.update(truck);
	}

	@Override
	public List<Truck> selectAllTruck() {
		return truckDao.selectAllTruck();
	}
	

}
