package com.ssafy.foodfind.data.repository.order

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.OrderItem
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OrderRepository {
    override suspend fun insertOrderRequest(order: Order): NetworkResponse<Int, ErrorResponse> {
        return apiService.insertOrderRequest(order)
    }

    override suspend fun selectOrderByUserIdRequest(userId : Int): NetworkResponse<List<Order>, ErrorResponse> {
        return apiService.selectOrderByUserIdRequest(userId)
    }

    override suspend fun selectOrderItemDetailByOrderId(orderId: Int): NetworkResponse<List<OrderItem>, ErrorResponse> {
        return apiService.selectOrderItemDetailByOrderId(orderId)
    }

    override suspend fun updateOrderToCancel(orderId: Int): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.updateOrderToCancel(orderId)
    }

    override suspend fun selectOrderByTruckId(truckId: Int): NetworkResponse<List<Order>, ErrorResponse> {
        return apiService.selectOrderByTruckId(truckId)
    }

    override suspend fun updateOrderStatus(order: Order): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.updateOrderStatus(order)
    }

    override suspend fun selectOrder(orderId: Int): NetworkResponse<Order, ErrorResponse> {
        return apiService.selectOrder(orderId)
    }
}