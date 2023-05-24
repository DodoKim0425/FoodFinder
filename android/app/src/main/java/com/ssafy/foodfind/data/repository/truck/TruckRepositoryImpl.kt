package com.ssafy.foodfind.data.repository.truck

import com.ssafy.foodfind.data.entity.*
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class TruckRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TruckRepository {
    override suspend fun getTruckCountRequest(ownerId: Int): NetworkResponse<Int, ErrorResponse> {
        return apiService.getTruckCountResponse(ownerId)
    }

    override suspend fun insertTruckRequest(truck: Truck): NetworkResponse<Int, ErrorResponse> {
        return apiService.insertTruckResponse(truck)
    }

    override suspend fun getMyTruckInfoRequest(ownerId: Int): NetworkResponse<Truck, ErrorResponse> {
        return apiService.getMyTruckInfoResponse(ownerId)
    }

    override suspend fun getAllTruckRequest(): NetworkResponse<List<Truck>, ErrorResponse> {
        return apiService.getAllTruckResponse()
    }

    override suspend fun updateTruckRequest(truck: Truck): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.updateTruckResponse(truck)
    }


    override suspend fun getAllTruckLocationRequest(): NetworkResponse<List<TruckLocation>, ErrorResponse> {
        return apiService.getAllTruckLocationResponse()
    }

    override suspend fun getTruckRequest(truckId: Int): NetworkResponse<Truck, ErrorResponse> {
        return apiService.getTruckResponse(truckId)
    }

    override suspend fun getTruckItemRequest(truckId: Int): NetworkResponse<List<FoodItem>, ErrorResponse> {
        return apiService.getTruckItemRequest(truckId)
    }
}