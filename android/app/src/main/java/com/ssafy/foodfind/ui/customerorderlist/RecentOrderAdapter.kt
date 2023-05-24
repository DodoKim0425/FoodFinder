package com.ssafy.foodfind.ui.customerorderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.databinding.ItemRecentOrderBinding

class RecentOrderAdapter :
    RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {

    private val orders: MutableList<Order> = mutableListOf()

    inner class ViewHolder(private val binding: ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentOrderBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun setOrders(newOrders: List<Order>) {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }
}
