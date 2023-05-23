package com.ssafy.foodfind.data.entity

data class Food(val name: String, val price: String)

data class Truck(
    val truckId: Int = 0,
    val ownerId: Int = 0,
    var name: String = "",
    val rating: Float = 0.0F,
    var description: String = "",
    var location: String = "",
    var currentStatus: String = ""
) : java.io.Serializable
