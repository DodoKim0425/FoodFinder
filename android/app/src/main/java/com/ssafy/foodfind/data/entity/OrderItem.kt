package com.ssafy.foodfind.data.entity

data class OrderItem(
    val customerName: String,
    val description: String,
    val itemId: Int,
    val itemName: String,
    val orderId: Int,
    val orderItemId: Int,
    val orderStatus: OrderStatus,
    val orderTime: String,
    val quantity: Int,
    val totalPrice: Int,
    val truckId: Int,
    val truckName: String,
    val userId: Int
) {
    fun getStatusText(): String {
        return when (orderStatus) {
            OrderStatus.RECEIVED -> "주문 접수됨"
            OrderStatus.COOKING -> "조리중"
            OrderStatus.DONE -> "완료"
            OrderStatus.CANCEL -> "주문 취소됨"
        }
    }

    fun getDateTime() : String {
        return orderTime.split("T")[0]
    }
}