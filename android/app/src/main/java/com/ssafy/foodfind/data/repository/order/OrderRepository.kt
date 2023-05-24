package com.ssafy.foodfind.data.repository.order

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.network.NetworkResponse
import retrofit2.http.Body

interface OrderRepository {
    suspend fun insertOrderRequest(order : Order): NetworkResponse<Int, ErrorResponse>
    suspend fun selectOrderByUserIdRequest(userId : Int): NetworkResponse<List<Order>, ErrorResponse>

}