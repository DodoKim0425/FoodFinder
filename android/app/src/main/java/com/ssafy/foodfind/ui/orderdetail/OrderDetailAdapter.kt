package com.ssafy.foodfind.ui.orderdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.databinding.ItemOrderDetailBinding

class OrderDetailAdapter(
    private val orderDetails: MutableList<OrderDetail>
) :
    RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetail) {
            binding.btnCancel.visibility = View.GONE
            binding.orderDetail = orderDetail
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderDetailBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }
}

