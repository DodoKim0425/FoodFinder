package com.ssafy.foodfind.ui.managetruck

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.BottomSheetFoodItemAddBinding
import com.ssafy.foodfind.databinding.BottomSheetTruckBinding

class ManageTruckItemBottomSheet(context: Context) : BottomSheetDialog(context) {
	private lateinit var binding: BottomSheetFoodItemAddBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_food_item_add, null, false)
		setContentView(binding.root)


	}
}