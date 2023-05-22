package com.ssafy.foodfind.ui.managetruck
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.databinding.ItemFoodBinding

class FoodTruckAdapter(private val foodList: List<FoodItem>) :
    RecyclerView.Adapter<FoodTruckAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: FoodItem) {
            binding.food = food
            binding.executePendingBindings()
        }
    }
}
