package com.ssafy.foodfind.ui.orderdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
    }
}