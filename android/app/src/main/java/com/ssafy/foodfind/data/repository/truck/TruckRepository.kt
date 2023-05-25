package com.ssafy.foodfind.data.repository.truck

import com.ssafy.foodfind.data.entity.*
import com.ssafy.foodfind.data.network.NetworkResponse

interface TruckRepository {
    suspend fun getTruckCountRequest(ownerId: Int): NetworkResponse<Int, ErrorResponse>
    suspend fun insertTruckRequest(truck: Truck): NetworkResponse<Int, ErrorResponse>
    suspend fun getMyTruckInfoRequest(ownerId: Int): NetworkResponse<Truck, ErrorResponse>
    suspend fun getAllTruckRequest(): NetworkResponse<List<Truck>, ErrorResponse>
    suspend fun updateTruckRequest(truck: Truck): NetworkResponse<Boolean, ErrorResponse>
    suspend fun getAllTruckLocationRequest(): NetworkResponse<List<TruckLocation>, ErrorResponse>
    suspend fun getTruckRequest(truckId: Int): NetworkResponse<Truck, ErrorResponse>
    suspend fun getTruckItemRequest(truckId: Int): NetworkResponse<List<FoodItem>, ErrorResponse>
}