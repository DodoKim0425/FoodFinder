package com.ssafy.foodfind.data.entity

import java.math.BigDecimal

data class FoodItem (
	val itemId : Int = 0,
	var truckId : Int = 0,
	var name : String = "",
	var description : String = "",
	var price : Int = 0,
	var status : String = "",
	val usingYN : Boolean = true
) : java.io.Serializable