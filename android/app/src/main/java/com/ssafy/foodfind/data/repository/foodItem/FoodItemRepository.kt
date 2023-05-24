package com.ssafy.foodfind.data.repository.foodItem

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.network.NetworkResponse

interface FoodItemRepository {
	suspend fun getFoodItemResponseByTruckId(truckId : Int): NetworkResponse<List<FoodItem>, ErrorResponse>
	suspend fun insertFoodItemResponse(foodItem : FoodItem): NetworkResponse<Boolean, ErrorResponse>

	suspend fun updateFoodItemResponse(foodItem: FoodItem): NetworkResponse<Boolean, ErrorResponse>

	suspend fun updateFoodItemToNotUseResponse(itemId : Int):NetworkResponse<Boolean, ErrorResponse>

	suspend fun insertAllFoodItemsResponse(foodItems : List<FoodItem>):NetworkResponse<Unit, ErrorResponse>
}