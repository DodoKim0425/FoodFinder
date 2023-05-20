package com.ssafy.foodfind.model.dao;

import java.util.List;

import com.ssafy.foodfind.model.dto.FoodItem;

public interface FoodItemDao {
	int insert(FoodItem foodItem);
	List<FoodItem> selectFoodItemByTruck(String truckId);
	FoodItem selectFoodItemById(String itemId);
	int updateFoodItemToNotUse(String itemId);
	int updateFoodItem(FoodItem foodItem);
}
