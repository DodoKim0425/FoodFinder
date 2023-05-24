package com.ssafy.foodfind.model.service;

import java.util.List;

import com.ssafy.foodfind.model.dto.FoodItem;

public interface FoodItemService {
	public boolean insert(FoodItem foodItem);
	public List<FoodItem> selectFoodItemByTruck(String truckId);
	public FoodItem selectFoodItemById(String itemId);
	public boolean updateFoodItemToNotUse(String itemId);
	public boolean updateFoodItem(FoodItem foodItem);
	public void insertAll(List<FoodItem> foodItems);
}
