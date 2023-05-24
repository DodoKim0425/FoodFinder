package com.ssafy.foodfind.data.repository.order

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.Query

interface OrderRepository {
    suspend fun insertOrderRequest(order : Order): NetworkResponse<Int, ErrorResponse>
    suspend fun selectOrderByUserIdRequest(userId : Int): NetworkResponse<List<Order>, ErrorResponse>

    suspend fun selectOrderItemDetailByOrderId(orderId : Int): NetworkResponse<List<Order>, ErrorResponse>

    suspend fun updateOrderToCancel(orderId : Int): NetworkResponse<Boolean, ErrorResponse>
}