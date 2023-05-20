package com.ssafy.foodfind.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.foodfind.model.dao.FoodItemDao;
import com.ssafy.foodfind.model.dto.FoodItem;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	@Autowired
	FoodItemDao foodItemDao;
	
	@Override
	@Transactional
	public boolean insert(FoodItem foodItem) {
    	int res=foodItemDao.insert(foodItem);
    	if(res==1)
    		return true;
    	else
    		return false;
	}

	@Override
	public List<FoodItem> selectFoodItemByTruck(String truckId) {
		return foodItemDao.selectFoodItemByTruck(truckId);
	}

	@Override
	public FoodItem selectFoodItemById(String itemId) {
		return foodItemDao.selectFoodItemById(itemId);
	}

	@Override
	@Transactional
	public boolean updateFoodItemToNotUse(String itemId) {
		int res=foodItemDao.updateFoodItemToNotUse(itemId);
    	if(res==1)
    		return true;
    	else
    		return false;
	}

	@Override
	@Transactional
	public boolean updateFoodItem(FoodItem foodItem) {
		int res=foodItemDao.updateFoodItem(foodItem);
    	if(res==1)
    		return true;
    	else
    		return false;
	}

}
