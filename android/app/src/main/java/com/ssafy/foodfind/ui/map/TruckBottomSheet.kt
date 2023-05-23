package com.ssafy.foodfind.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.BottomSheetTruckBinding
import com.ssafy.foodfind.ui.truckinfo.TruckInfoActivity

class TruckBottomSheet(context: Context, private val truck: Truck) : BottomSheetDialog(context) {
    private lateinit var binding: BottomSheetTruckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_truck, null, false)
        setContentView(binding.root)

        binding.truck = truck
        initButton()
    }

    private fun initButton() {
        binding.title.setOnClickListener {
            val intent = Intent(context, TruckInfoActivity::class.java)
            intent.putExtra("truckId", truck.truckId)
            context.startActivity(intent)
        }
    }
}
