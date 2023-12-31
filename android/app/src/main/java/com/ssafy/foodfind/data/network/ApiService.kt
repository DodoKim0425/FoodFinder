package com.ssafy.foodfind.data.network


import com.ssafy.foodfind.data.entity.*
import retrofit2.http.*

interface ApiService {
    //user
    @POST("/rest/user/login")
    suspend fun getLoginResponse(@Body user: Map<String, String>): NetworkResponse<User, ErrorResponse>

    @GET("/rest/user/checkphone")
    suspend fun checkPhoneNumberResponse(@Query("phoneNumber") phoneNumber: String): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/user/info")
    suspend fun getUserInfoResponse(@Query("phoneNumber") phoneNumber: String): NetworkResponse<User, ErrorResponse>

    @POST("/rest/user/insert")
    suspend fun insertUserResponse(@Body user: User): NetworkResponse<Boolean, ErrorResponse>

    @POST("/rest/user/update")
    suspend fun updateUserResponse(@Body user: User): NetworkResponse<Boolean, ErrorResponse>

    //truck
    @GET("/rest/truck/getUserTruckCount")
    suspend fun getTruckCountResponse(@Query("ownerId") ownerId: Int): NetworkResponse<Int, ErrorResponse>

    @POST("/rest/truck/insert")
    suspend fun insertTruckResponse(@Body truck: Truck): NetworkResponse<Int, ErrorResponse>

    @GET("/rest/truck/myTruckInfo")
    suspend fun getMyTruckInfoResponse(@Query("ownerId") ownerId: Int): NetworkResponse<Truck, ErrorResponse>

    @GET("/rest/truck/selectAllTruck")
    suspend fun getAllTruckResponse(): NetworkResponse<List<Truck>, ErrorResponse>

    @PUT("/rest/truck/update")
    suspend fun updateTruckResponse(@Body truck: Truck): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/truck/selectTruckLocations")
    suspend fun getAllTruckLocationResponse(): NetworkResponse<List<TruckLocation>, ErrorResponse>

    @GET("/rest/truck/selectTruckByTruckId")
    suspend fun getTruckResponse(@Query("truckId") truckId: Int): NetworkResponse<Truck, ErrorResponse>

    @GET("/rest/foodItem/selectByTruckId")
    suspend fun getTruckItemRequest(@Query("truckId") truckId: Int): NetworkResponse<List<FoodItem>, ErrorResponse>


    //FoodItem
    @GET("/rest/foodItem/selectByTruckId")
    suspend fun getFoodItemsResponseByTruckId(@Query("truckId") truckId: Int): NetworkResponse<List<FoodItem>, ErrorResponse>

    @POST("/rest/foodItem/insert")
    suspend fun insertFoodItemResponse(@Body foodItem: FoodItem): NetworkResponse<Boolean, ErrorResponse>

    @PUT("/rest/foodItem/updateItem")
    suspend fun updateFoodItemResponse(@Body foodItem: FoodItem): NetworkResponse<Boolean, ErrorResponse>

    @PUT("/rest/foodItem/updateItemToNotUse/{itemId}")
    suspend fun updateFoodItemToNotUseResponse(@Path("itemId") itemId: Int): NetworkResponse<Boolean, ErrorResponse>

    @POST("/rest/foodItem/insertAll")
    suspend fun insertAllFoodItemResponse(@Body foodItems: List<FoodItem>): NetworkResponse<Unit, ErrorResponse>

    //order
    @POST("/rest/order/insert")
    suspend fun insertOrderRequest(@Body order: Order): NetworkResponse<Int, ErrorResponse>

    @GET("/rest/order/selectOrderByUserId")
    suspend fun selectOrderByUserIdRequest(@Query("userId") userId: Int): NetworkResponse<List<Order>, ErrorResponse>

    @GET("/rest/order/selectOrderItemDetailByOrderId")
    suspend fun selectOrderItemDetailByOrderId(@Query("orderId") orderId: Int): NetworkResponse<List<OrderItem>, ErrorResponse>

    @PUT("/rest/order/updateOrderToCancel")
    suspend fun updateOrderToCancel(@Query("orderId") orderId: Int): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/order/selectOrderByTruckId")
    suspend fun selectOrderByTruckId(@Query("truckId") truckId: Int): NetworkResponse<List<Order>, ErrorResponse>

    @PUT("/rest/order/updateOrderStatus")
    suspend fun updateOrderStatus(@Body order: Order): NetworkResponse<Boolean, ErrorResponse>

    @GET("/rest/order/selectOrder")
    suspend fun selectOrder(@Query("orderId") orderId: Int): NetworkResponse<Order, ErrorResponse>


    //comment
    @GET("/rest/comment/getCommentByTruck")
    suspend fun getCommentByTruckRequest(@Query("truckId") truckId: Int): NetworkResponse<List<Comment>, ErrorResponse>

    @DELETE("/rest/comment/delete/{id}")
    suspend fun deleteCommentRequest(@Path("id") commentId: Int): NetworkResponse<Boolean, ErrorResponse>

    @PUT("/rest/comment/update")
    suspend fun updateCommentRequest(@Body comment: Comment): NetworkResponse<Boolean, ErrorResponse>


    @POST("/rest/comment/insert")
    suspend fun insertCommentRequest(@Body comment: Comment):NetworkResponse<Boolean, ErrorResponse>

}