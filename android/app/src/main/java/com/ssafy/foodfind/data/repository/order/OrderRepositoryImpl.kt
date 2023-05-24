package com.ssafy.foodfind.data.repository.order

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OrderRepository {
    override suspend fun insertOrderRequest(order: Order): NetworkResponse<Int, ErrorResponse> {
        return apiService.insertOrderRequest(order)
    }
}