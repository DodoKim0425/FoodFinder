package com.ssafy.foodfind.data.repository.foodItem

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.network.NetworkResponse

interface FoodItemRepository {
	suspend fun getFoodItemResponseByTruckId(truckId : Int): NetworkResponse<List<FoodItem>, ErrorResponse>
	suspend fun insertFoodItemResponse(foodItem : FoodItem): NetworkResponse<Boolean, ErrorResponse>

}