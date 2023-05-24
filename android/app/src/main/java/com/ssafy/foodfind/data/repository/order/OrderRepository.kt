package com.ssafy.foodfind.data.repository.order

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.network.NetworkResponse

interface OrderRepository {
    suspend fun insertOrderRequest(order : Order): NetworkResponse<Int, ErrorResponse>
}