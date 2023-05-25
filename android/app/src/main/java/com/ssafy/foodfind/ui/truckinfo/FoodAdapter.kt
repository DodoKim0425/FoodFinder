package com.ssafy.foodfind.ui.truckinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.databinding.ItemFoodBinding

class FoodAdapter(private val items: List<FoodItem>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodItem) {
            binding.food = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(layoutInflater, parent, false)

        val viewHolder = ViewHolder((binding))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = items[position]
                itemClickListener?.invoke(item)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private var itemClickListener: ((FoodItem) -> Unit)? = null
    fun setItemClickListener(listener: (FoodItem) -> Unit) {
        itemClickListener = listener
    }
}