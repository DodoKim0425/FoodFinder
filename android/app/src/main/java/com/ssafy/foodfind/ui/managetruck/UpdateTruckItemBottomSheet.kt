package com.ssafy.foodfind.ui.managetruck

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.databinding.BottomSheetFoodItemAddBinding
import com.ssafy.foodfind.databinding.BottomSheetFoodItemUpdateBinding
import kotlin.math.log

private const val TAG = "UpdateTruckItemBottomSh_싸피"
class UpdateTruckItemBottomSheet (context: Context, private var foodItem:FoodItem) : BottomSheetDialog(context){
	private lateinit var binding: BottomSheetFoodItemUpdateBinding
	var listener: OnSendFromBottomSheetDialog? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_food_item_update, null, false)

		Log.d(TAG, "onCreate: $foodItem")

		setContentView(binding.root)

		setData()
		binding.foodItemUpdateBtn.setOnClickListener {
			var name = binding.etUpdatedFoodItemName.text.toString()
			var description = binding.etUpdatedFoodItemDescription.text.toString()
			var price = (binding.etUpdatedFoodItemPrcie.text.toString())
			var status =""
			when(binding.updateBottomSheetRadioGroup.checkedRadioButtonId){
				R.id.foodItemAvailable->status ="AVAILABLE"
				else->status="SOLD_OUT"
			}
			if(name!="" && description!="" && price!=""){
				listener?.sendValue(FoodItem(0,0,name, description, Integer.parseInt(price), status, true))
				dismiss()
			}else{
				Toast.makeText(context, "모든 입력창을 채워주세요", Toast.LENGTH_SHORT).show()
			}

		}

	}

	private fun setData(){
		binding.etUpdatedFoodItemName.setText(foodItem.name)
		binding.etUpdatedFoodItemPrcie.setText(foodItem.price.toString())
		binding.etUpdatedFoodItemDescription.setText(foodItem.description)
		if(foodItem.status=="AVAILABLE"){
			binding.foodItemNotAvailable.isChecked=false
			binding.foodItemAvailable.isChecked=true
		}else{
			binding.foodItemNotAvailable.isChecked=true
			binding.foodItemAvailable.isChecked=false
		}
	}

	interface OnSendFromBottomSheetDialog{
		fun sendValue(value : FoodItem)
	}
}