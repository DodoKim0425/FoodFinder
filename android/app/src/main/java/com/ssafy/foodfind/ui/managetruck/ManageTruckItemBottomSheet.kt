package com.ssafy.foodfind.ui.managetruck

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.BottomSheetFoodItemAddBinding
import com.ssafy.foodfind.databinding.BottomSheetTruckBinding

class ManageTruckItemBottomSheet(context: Context) : BottomSheetDialog(context) {
	private lateinit var binding: BottomSheetFoodItemAddBinding
	var listener: OnSendFromBottomSheetDialog? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_food_item_add, null, false)

		setContentView(binding.root)
		binding.foodItemAddBtn.setOnClickListener {
			var name = binding.etNewFoodItemName.text.toString()
			var description = binding.etNewFoodItemDescription.text.toString()
			var price = (binding.etNewFoodItemPrcie.text.toString())
			if(name!="" && description!="" && price!=""){
				listener?.sendValue(FoodItem(0,0,name, description, Integer.parseInt(price), "AVAILABLE", true))
				dismiss()
			}else{
				Toast.makeText(context, "모든 입력창을 채워주세요", Toast.LENGTH_SHORT).show()
			}

		}

	}

	interface OnSendFromBottomSheetDialog{
		fun sendValue(value : FoodItem)
	}
}