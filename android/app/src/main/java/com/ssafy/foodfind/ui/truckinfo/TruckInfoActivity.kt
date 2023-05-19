package com.ssafy.foodfind.ui.truckinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.R
import com.ssafy.foodfind.databinding.ActivityCustomerOrderListBinding
import com.ssafy.foodfind.databinding.ActivityTruckInfoBinding
import com.ssafy.foodfind.ui.map.MapActivity
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTruckInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTruckInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.btnShoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}