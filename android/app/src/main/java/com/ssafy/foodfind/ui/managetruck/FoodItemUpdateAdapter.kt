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

class FoodItemUpdateAdapter : ListAdapter<FoodItem, FoodItemUpdateAdapter.CustomViewHolder>(itemComparator){

	companion object itemComparator: DiffUtil.ItemCallback<FoodItem>(){
		override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
			return oldItem.hashCode()==newItem.hashCode()
		}
		override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
			return oldItem==newItem
		}
	}

	inner class CustomViewHolder(private val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root){
		fun bindInfo(data:FoodItem, position: Int){
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
			bindInfo(getItem(position), position)
		}
	}

	fun removeItem(position: Int){
		val newList=currentList.toMutableList()
		newList.removeAt(position)
		submitList(newList)
	}

	interface ItemClickListener{
		fun onClick(data:FoodItem, position: Int)
	}
	lateinit var itemClickListener: ItemClickListener

}