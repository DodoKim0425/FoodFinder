package com.ssafy.foodfind.data.repository.truck

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.network.NetworkResponse

interface TruckRepository {
    suspend fun getTruckCountResponse(ownerId : Int): NetworkResponse<Int, ErrorResponse>
    suspend fun insertTruckResponse(truck : Truck): NetworkResponse<Boolean, ErrorResponse>
    suspend fun getMyTruckInfo(ownerId : Int): NetworkResponse<Truck, ErrorResponse>
    suspend fun getAllTruck(): NetworkResponse<List<Truck>, ErrorResponse>
    suspend fun updateTruck(truck : Truck): NetworkResponse<Boolean, ErrorResponse>
}