package com.ssafy.foodfind.data.entity

data class Comment (
	var commentId : Int,
	var userId : Int,
	var truckId : Int,
	var rating: Float = 0.0F,
	var content : String,
	var username: String
	)