package com.ssafy.foodfind.data.entity

import com.ssafy.foodfind.R

data class Food(val name: String, val price: Int)

enum class TruckStatus {
    OPEN, CLOSED
}

data class Truck(
    val truckId: Int = 0,
    var ownerId: Int = 0,
    var name: String = "",
    val rating: Float = 0.0F,
    var description: String = "",
    var location: String = "",
    var currentStatus: TruckStatus,
) : java.io.Serializable
 {
    fun getStatusText(): String {
        return when (currentStatus) {
            TruckStatus.OPEN -> "영업중"
            TruckStatus.CLOSED -> "휴업중"
        }
    }

    fun getStatusColor(): Int {
        return when (currentStatus) {
            TruckStatus.OPEN -> R.color.done
            TruckStatus.CLOSED -> R.color.cancel
        }
    }
}

data class TruckLocation(
    val truckId: Int = 0,
    val location: String = "",
) {
    val latitude: Double
        get() = location.split("/")[0].toDoubleOrNull() ?: 0.0

    val longitude: Double
        get() = location.split("/")[1].toDoubleOrNull() ?: 0.0
}

