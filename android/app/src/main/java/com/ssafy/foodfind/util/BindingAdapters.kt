package com.ssafy.foodfind.util

import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.math.BigDecimal
import java.text.DecimalFormat

private const val TAG = "BindingAdapters_싸피"

@BindingAdapter("checkTruckOpen")
fun checkTruckOpen(view:RadioButton, truckStatus:String){
	Log.d(TAG, "checkTruckOpen: ${truckStatus}")
	if(truckStatus=="OPEN"){
		view.isChecked=true
	}else{
		view.isChecked=false
	}
}

@BindingAdapter("checkTruckClosed")
fun checkTruckClosed(view:RadioButton, truckStatus:String){
	if(truckStatus=="CLOSED"){
		view.isChecked=true
	}else{
		view.isChecked=false
	}
}

@BindingAdapter("bigDecimalToPriceString")
fun bigDecimalToPriceString(view: TextView, bigDecimal: BigDecimal){
	val df = DecimalFormat("#,##0")
	view.setText(df.format(bigDecimal)+"원")
}