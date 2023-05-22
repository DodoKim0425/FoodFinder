package com.ssafy.foodfind.ui.ownerorderlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.databinding.ActivityOwnerOrderListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerOrderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerOrderListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOwnerOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.buttonStop.setOnClickListener {

        }
    }
}