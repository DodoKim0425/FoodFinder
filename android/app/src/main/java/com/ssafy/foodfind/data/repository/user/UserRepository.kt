package com.ssafy.foodfind.data.repository.user

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.NetworkResponse

interface UserRepository {
    suspend fun login(user: HashMap<String, String>): NetworkResponse<User, ErrorResponse>
    suspend fun checkPhoneNumber(phoneNumber : String): NetworkResponse<Boolean, ErrorResponse>
    suspend fun getUser(phoneNumber : String): NetworkResponse<User, ErrorResponse>
    suspend fun insertUser(user : User): NetworkResponse<Boolean, ErrorResponse>
    suspend fun updateUser(user : User): NetworkResponse<Boolean, ErrorResponse>
}