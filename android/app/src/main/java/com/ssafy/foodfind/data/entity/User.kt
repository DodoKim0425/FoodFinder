package com.ssafy.foodfind.data.entity

data class User(
    val userId: Int = 0,
    val username: String = "",
    val phoneNumber: String = "",
    val userType: String = "CUSTOMER",
    val password: String = ""
)
