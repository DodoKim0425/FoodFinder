package com.ssafy.foodfind.ui.managetruck

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.databinding.ItemFoodBinding
import kotlin.math.log

private const val TAG = "FoodItemUpdateAdapter_싸피"
