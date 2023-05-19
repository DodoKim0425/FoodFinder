package com.ssafy.foodfind.ui.truckinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.databinding.BottomSheetOrdrDetailBinding
import dagger.hilt.android.AndroidEntryPoint

class OrderDetailBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetOrdrDetailBinding
    private lateinit var orderDetail: OrderDetail

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_ordr_detail,
            container,
            false
        )
        binding.lifecycleOwner = this

        // Get the OrderDetail object from arguments
        val arguments = arguments
        if (arguments != null) {
            orderDetail = arguments.getSerializable(ARG_ORDER_DETAIL) as OrderDetail
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
            // Add the orderDetail to the cart or perform any necessary action
            dismiss()
        }

        return binding.root
    }

    private fun updateTotalPrice() {
        val totalPrice = orderDetail.count * Integer.parseInt(orderDetail.price)
        binding.tvTotalPrice.text = String.format("%,d원", totalPrice)
    }

    companion object {
        private const val ARG_ORDER_DETAIL = "arg_order_detail"

        fun newInstance(orderDetail: OrderDetail): OrderDetailBottomSheet {
            val args = Bundle()
            args.putSerializable(ARG_ORDER_DETAIL, orderDetail)

            val fragment = OrderDetailBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }
}
