package com.ssafy.foodfind.ui.customerorderlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.R
import com.ssafy.foodfind.databinding.ActivityCustomerOrderListBinding
import com.ssafy.foodfind.databinding.ActivityNotificationBinding

class CustomerOrderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerOrderListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}