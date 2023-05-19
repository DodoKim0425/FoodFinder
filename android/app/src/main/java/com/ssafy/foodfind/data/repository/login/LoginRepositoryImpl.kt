package com.ssafy.foodfind.data.repository.login

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {
    override suspend fun login(user: HashMap<String, String>): NetworkResponse<User, ErrorResponse> {
        return apiService.getLoginResponse(user)
    }
}