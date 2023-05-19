package com.ssafy.foodfind.data.repository.login

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.NetworkResponse

interface LoginRepository {
    suspend fun login(user: HashMap<String, String>): NetworkResponse<User, ErrorResponse>
}