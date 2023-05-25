package com.ssafy.foodfind.ui.orderdetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.OrderStatus
import com.ssafy.foodfind.databinding.ActivityOrderDetailBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.shoppingcart.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding>(R.layout.activity_order_detail) {
    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var adapter: OrderDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOrder()
        initRecyclerView()
        observeData()
    }

    private fun initOrder() {
        val orderId = intent.getIntExtra("orderId", -1)
        if(orderId != -1) {
            viewModel.getDetailOrder(orderId)
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.orderCancel.setOnClickListener {
            if(binding.order?.orderStatus == OrderStatus.RECEIVED) {
                viewModel.updateOrderToCancel(orderId)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = OrderDetailAdapter()
        binding.recyclerViewOrderItems.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerViewOrderItems.adapter = adapter
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@OrderDetailActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@OrderDetailActivity)
            isLoading.observe(this@OrderDetailActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            orderItems.observe(this@OrderDetailActivity) {
                if(it.isNotEmpty()) {
                    val order = it[0]
                    binding.order = order
                    if((SharedPrefs.getUserInfo()?.userId ?: -1) == order.userId) {
                        binding.orderStart.visibility = View.GONE
                    }
                    if(order.orderStatus != OrderStatus.RECEIVED) {
                        binding.orderCancel.isEnabled = false
                    }
                    adapter.addOrderDetail(it)
                }
            }

            isCancel.observe(this@OrderDetailActivity) {
                if(it == true) {
                    showToast("주문이 취소되었습니다.")
                    finish()
                }
            }
        }
    }
}