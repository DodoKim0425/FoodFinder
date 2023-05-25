package com.ssafy.foodfind.data.entity

data class Comment (
	var commentId : Int=0,
	var userId : Int =0,
	var truckId : Int=0,
	var rating: Float = 0.0F,
	var content : String="",
	var username: String=""
	)