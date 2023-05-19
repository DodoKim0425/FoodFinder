package com.ssafy.foodfind.data.network

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //로그인
    @POST("/rest/user/login")
    suspend fun getLoginResponse(@Body user : Map<String, String>): NetworkResponse<User, ErrorResponse>
}