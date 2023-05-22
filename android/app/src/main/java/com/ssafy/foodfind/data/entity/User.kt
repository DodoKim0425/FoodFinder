package com.ssafy.foodfind.data.entity

data class User(
    val userId: Int = -1,
    val username: String = "",
    val phoneNumber: String = "",
    val userType: String = "CUSTOMER",
    val password: String = ""
)
