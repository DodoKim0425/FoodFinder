package com.ssafy.foodfind.data.entity

import com.ssafy.foodfind.R
import java.io.Serializable
import java.sql.Timestamp

enum class OrderStatus {
    RECEIVED, COOKING, DONE, CANCEL
}

data class Order(
    val orderId: Int,
    val userId: Int,
    val truckId: Int,
    val name: String,
    val customerName: String,
    val description: String?,
    val totalPrice: Int,
    val orderTime: String,
    val orderStatus: OrderStatus,
    var items : List<HashMap<String, String>>
) {
    fun getStatusText(): String {
        return when (orderStatus) {
            OrderStatus.RECEIVED -> "주문 접수됨"
            OrderStatus.COOKING -> "조리중"
            OrderStatus.DONE -> "완료"
            OrderStatus.CANCEL -> "주문 취소됨"
        }
    }

    fun getStatusColor(): Int {
        return when (orderStatus) {
            OrderStatus.RECEIVED -> R.color.received
            OrderStatus.COOKING -> R.color.white
            OrderStatus.DONE -> R.color.done
            OrderStatus.CANCEL -> R.color.cancel
        }
    }
}

data class OrderDetail(val item: FoodItem, var count: Int) : Serializable
