package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.R
import com.ssafy.foodfind.databinding.ActivityCustomerOrderListBinding
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity

class ManageTruckItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageTruckItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageTruckItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnUpdateTruck.setOnClickListener {
            val intent = Intent(this, ManageTruckActivity::class.java)
            startActivity(intent)
        }
    }
}