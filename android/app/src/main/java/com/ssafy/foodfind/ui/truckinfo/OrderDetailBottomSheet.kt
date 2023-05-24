package com.ssafy.foodfind.ui.truckinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.BottomSheetOrdrDetailBinding

class OrderDetailBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetOrdrDetailBinding
    private lateinit var orderDetail: OrderDetail
    private lateinit var truck: Truck


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_ordr_detail,
            container,
            false
        )
        binding.lifecycleOwner = this

        val arguments = arguments
        if (arguments != null) {
            orderDetail = arguments.getSerializable(ARG_ORDER_DETAIL) as OrderDetail
            truck = arguments.getSerializable(ARG_TRUCK) as Truck
            binding.orderDetail = orderDetail
        }

        binding.btnMinus.setOnClickListener {
            if (orderDetail.count > 0) {
                orderDetail.count--
                binding.tvCount.text = orderDetail.count.toString()
                updateTotalPrice()
            }
        }

        binding.btnPlus.setOnClickListener {
            orderDetail.count++
            binding.tvCount.text = orderDetail.count.toString()
            updateTotalPrice()
        }

        binding.buttonAddCart.setOnClickListener {
            SharedPrefs.addShoppingList(orderDetail, truck)
            dismiss()
        }

        return binding.root
    }

    private fun updateTotalPrice() {
        val totalPrice = orderDetail.count * orderDetail.item.price
        binding.tvTotalPrice.text = String.format("%,d원", totalPrice)
    }

    companion object {
        private const val ARG_ORDER_DETAIL = "arg_order_detail"
        private const val ARG_TRUCK = "arg_truck"
        fun newInstance(orderDetail: OrderDetail, truck: Truck): OrderDetailBottomSheet {
            val args = Bundle()
            args.putSerializable(ARG_ORDER_DETAIL, orderDetail)
            args.putSerializable(ARG_TRUCK, truck)

            val fragment = OrderDetailBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }
}
