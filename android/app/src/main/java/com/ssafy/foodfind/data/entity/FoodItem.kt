package com.ssafy.foodfind.data.entity

import java.math.BigDecimal

data class FoodItem (
	val itemId : Int = 0,
	val truckId : Int = 0,
	var name : String = "",
	var description : String = "",
	var price : BigDecimal = BigDecimal(0),
	var status : String = "",
	val usingYN : Boolean = true
)