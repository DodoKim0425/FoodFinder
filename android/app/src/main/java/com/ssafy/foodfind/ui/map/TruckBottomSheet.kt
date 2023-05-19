package com.ssafy.foodfind.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.Truck
import com.ssafy.foodfind.databinding.BottomSheetTruckBinding


class TruckBottomSheet(context: Context, private val truck: Truck) : BottomSheetDialog(context) {
    private lateinit var binding: BottomSheetTruckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_truck, null, false)
        setContentView(binding.root)

        // 바텀시트에 표시할 트럭 정보 설정
        binding.truck = truck
    }
}
