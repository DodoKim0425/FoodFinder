package com.ssafy.foodfind.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("message")
    val message : String?,
    @SerializedName("error")
    val error : String
)