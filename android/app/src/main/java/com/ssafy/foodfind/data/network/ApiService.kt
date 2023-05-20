package com.ssafy.foodfind.data.network

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/rest/user/login")
    suspend fun getLoginResponse(@Body user : Map<String, String>): NetworkResponse<User, ErrorResponse>

    @GET("/rest/user/checkphone")
    suspend fun checkPhoneNumberResponse(@Query("phoneNumber") phoneNumber : String): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/user/info")
    suspend fun getUserInfoResponse(@Query("phoneNumber") phoneNumber : String): NetworkResponse<User, ErrorResponse>

    @POST("/rest/user/insert")
    suspend fun insertUserResponse(@Body user : User): NetworkResponse<Boolean, ErrorResponse>

    @POST("/rest/user/update")
    suspend fun updateUserResponse(@Body user : User): NetworkResponse<Boolean, ErrorResponse>
}