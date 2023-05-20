package com.ssafy.foodfind.data.repository.truck

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class TruckRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TruckRepository {
    override suspend fun getTruckCountResponse(ownerId: Int): NetworkResponse<Int, ErrorResponse> {
        return apiService.getTruckCountResponse(ownerId)
    }

    override suspend fun insertTruckResponse(truck: Truck): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.insertTruckResponse(truck)
    }

    override suspend fun getMyTruckInfo(ownerId: Int): NetworkResponse<Truck, ErrorResponse> {
        return apiService.getMyTruckInfoResponse(ownerId)
    }

    override suspend fun getAllTruck(): NetworkResponse<List<Truck>, ErrorResponse> {
        return apiService.getAllTruckResponse()
    }

    override suspend fun updateTruck(truck: Truck): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.updateTruckResponse(truck)
    }

}