package com.ssafy.foodfind.data.network

import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.entity.User
import retrofit2.http.*

interface ApiService {
    //user
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

    //truck
    @GET("/rest/truck/getUserTruckCount")
    suspend fun getTruckCountResponse(@Query("ownerId") ownerId : Int): NetworkResponse<Int, ErrorResponse>

    @POST("/rest/truck/insert")
    suspend fun insertTruckResponse(@Body truck : Truck): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/truck/myTruckInfo")
    suspend fun getMyTruckInfoResponse(@Query("ownerId") ownerId : Int): NetworkResponse<Truck, ErrorResponse>

    @GET("/rest/truck/selectAllTruck")
    suspend fun getAllTruckResponse(): NetworkResponse<List<Truck>, ErrorResponse>

    @PUT("/rest/truck/update")
    suspend fun updateTruckResponse(@Body truck : Truck): NetworkResponse<Boolean, ErrorResponse>

    //FoodItem
    @GET("/rest/foodItem/selectByTruckId")
    suspend fun getFoodItemsResponseByTruckId(@Query("truckId") truckId : Int): NetworkResponse<List<FoodItem>, ErrorResponse>
}