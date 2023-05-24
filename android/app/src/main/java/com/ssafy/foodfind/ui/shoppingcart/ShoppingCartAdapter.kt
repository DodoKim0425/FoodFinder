package com.ssafy.foodfind.ui.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.databinding.ItemOrderDetailBinding

class ShoppingCartAdapter(
    private val orderDetails: MutableList<OrderDetail>,
    private val onCancelClickListener: (OrderDetail) -> Unit
) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetail) {
            binding.orderDetail = orderDetail
            binding.executePendingBindings()

            binding.btnCancel.setOnClickListener {
                onCancelClickListener.invoke(orderDetail)
            }
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

    fun removeItem(item : OrderDetail) {
        orderDetails.remove(item)
        SharedPrefs.removeShoppingList(item)
        notifyDataSetChanged()
    }
}

