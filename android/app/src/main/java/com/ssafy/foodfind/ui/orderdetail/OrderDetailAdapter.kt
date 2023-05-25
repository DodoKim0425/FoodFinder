package com.ssafy.foodfind.ui.orderdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.OrderCount
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.data.entity.OrderItem
import com.ssafy.foodfind.databinding.ItemOrderCountBinding
import com.ssafy.foodfind.databinding.ItemOrderDetailBinding

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    private val orderDetails: MutableList<OrderItem> = mutableListOf()

    inner class ViewHolder(private val binding: ItemOrderCountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderItem: OrderItem) {
            binding.orderItem = orderItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderCountBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }

    fun addOrderDetail(details: List<OrderItem>) {
        orderDetails.clear()
        orderDetails.addAll(details)
        notifyDataSetChanged()
    }
}

