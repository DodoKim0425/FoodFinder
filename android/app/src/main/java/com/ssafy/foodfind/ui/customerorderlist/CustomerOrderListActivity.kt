package com.ssafy.foodfind.ui.customerorderlist


import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.databinding.ActivityCustomerOrderListBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.shoppingcart.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerOrderListActivity :
    BaseActivity<ActivityCustomerOrderListBinding>(R.layout.activity_customer_order_list) {
    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var adapter: RecentOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        initRecyclerView()
        observeData()
    }

    private fun initButton() {
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        viewModel.getRecentOrders(SharedPrefs.getUserInfo()?.userId ?: 0)
        adapter = RecentOrderAdapter()
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerview.adapter = adapter
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@CustomerOrderListActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@CustomerOrderListActivity)
            isLoading.observe(this@CustomerOrderListActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            orders.observe(this@CustomerOrderListActivity) {
                adapter.setOrders(it)
            }
        }
    }
}