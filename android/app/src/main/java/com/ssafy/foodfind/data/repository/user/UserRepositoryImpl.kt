package com.ssafy.foodfind.data.repository.user

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun login(user: HashMap<String, String>): NetworkResponse<User, ErrorResponse> {
        return apiService.getLoginResponse(user)
    }

    override suspend fun checkPhoneNumber(phoneNumber: String): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.checkPhoneNumberResponse(phoneNumber)
    }

    override suspend fun getUser(phoneNumber: String): NetworkResponse<User, ErrorResponse> {
        return apiService.getUserInfoResponse(phoneNumber)
    }

    override suspend fun insertUser(user: User): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.insertUserResponse(user)
    }

    override suspend fun updateUser(user: User): NetworkResponse<Boolean, ErrorResponse> {
        return apiService.updateUserResponse(user)
    }
}