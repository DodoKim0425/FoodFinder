package com.ssafy.foodfind.data.network


import com.ssafy.foodfind.data.entity.*
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


    @GET("/rest/truck/selectTruckLocations")
    suspend fun getAllTruckLocationResponse(): NetworkResponse<List<TruckLocation>, ErrorResponse>
    @GET("/rest/truck/selectTruckByTruckId")
    suspend fun getTruckResponse(@Query("truckId") truckId : Int): NetworkResponse<Truck, ErrorResponse>

    @GET("/rest/foodItem/selectByTruckId")
    suspend fun getTruckItemRequest(@Query("truckId") truckId : Int): NetworkResponse<List<FoodItem>, ErrorResponse>

    //order
    @POST("/rest/order/insert")
    suspend fun insertOrderRequest(@Body order : Order): NetworkResponse<Int, ErrorResponse>

}