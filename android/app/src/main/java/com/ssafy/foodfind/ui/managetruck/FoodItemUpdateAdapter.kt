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
class FoodItemUpdateAdapter : ListAdapter<FoodItem, FoodItemUpdateAdapter.CustomViewHolder>(ItemComparator){
    companion object ItemComparator: DiffUtil.ItemCallback<FoodItem>(){
        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }
        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            Log.d(TAG, "areContentsTheSame: $oldItem")
            Log.d(TAG, "areContentsTheSame: $newItem")
            return oldItem==newItem
        }
    }

    inner class CustomViewHolder(private val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root){
        fun bindInfo(data:FoodItem, position: Int){
            Log.d(TAG, "bindInfo: biding!!! $data")
            binding.food=data
            binding.root.setOnClickListener {
                itemClickListener.onClick(data, position)
            }
        }
        fun setBackgound(color:Int){
            itemView.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.apply{
            bindInfo(getItem(adapterPosition), position)
        }
    }


    interface ItemClickListener{
        fun onClick(data:FoodItem, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

}