package com.ssafy.foodfind.ui.ownerorderlist

import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.databinding.ActivityOwnerOrderListBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.shoppingcart.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerOrderListActivity :
    BaseActivity<ActivityOwnerOrderListBinding>(R.layout.activity_owner_order_list) {

    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var adapter: TruckOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.layoutSwipe.setOnRefreshListener {
            initRecyclerView()
            binding.layoutSwipe.isRefreshing = false
        }

        observeData()
    }

    private fun initRecyclerView() {
        val truckId = SharedPrefs.getTruckId()
        if(truckId != -1) {
            viewModel.getRecentOrdersByTruckId(truckId)
            adapter = TruckOrderAdapter()
            binding.recyclerview.adapter = adapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@OwnerOrderListActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@OwnerOrderListActivity)
            isLoading.observe(this@OwnerOrderListActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            orders.observe(this@OwnerOrderListActivity) {
                adapter.setOrders(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }
}