package com.ssafy.foodfind.ui.truckinfo

import android.app.AlertDialog
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
            if (SharedPrefs.getShoppingList().size == 0) {
                SharedPrefs.addShoppingList(orderDetail, truck)
            } else {
                val truckId = SharedPrefs.getShoppingList()[0].item.truckId
                if (truckId == truck.truckId) {
                    SharedPrefs.addShoppingList(orderDetail, truck)
                    dismiss()
                } else {
                    showTruckMismatchDialog()
                    dismiss()
                }
            }
        }

        return binding.root
    }

    private fun updateTotalPrice() {
        val totalPrice = orderDetail.count * orderDetail.item.price
        binding.tvTotalPrice.text = String.format("%,d원", totalPrice)
    }


    private fun showTruckMismatchDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("장바구니 정보 삭제")
        dialogBuilder.setMessage("이전 가게에서 장바구니에 넣어둔 메뉴가 삭제됩니다. 계속 진행하시겠습니까?")
        dialogBuilder.setPositiveButton("확인") { dialog, _ ->
            SharedPrefs.addShoppingList(orderDetail, truck)
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
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
