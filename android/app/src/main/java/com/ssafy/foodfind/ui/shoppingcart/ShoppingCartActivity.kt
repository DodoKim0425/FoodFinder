package com.ssafy.foodfind.ui.shoppingcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.foodfind.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
    }
}