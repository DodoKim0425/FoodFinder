package com.ssafy.foodfind.data.entity

data class Food(val name: String, val price: String)

data class Truck(
    val truckId: Int = 0,
    val ownerId: Int = 0,
    val name: String = "",
    val rating: Float = 0.0F,
    val description: String = "",
    val location: String = "",
    val currentStatus: String = ""
)
