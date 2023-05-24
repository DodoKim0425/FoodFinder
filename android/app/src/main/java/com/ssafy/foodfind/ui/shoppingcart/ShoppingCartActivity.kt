package com.ssafy.foodfind.ui.shoppingcart

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.SharedPrefs.getShoppingList
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.OrderStatus
import com.ssafy.foodfind.databinding.ActivityShoppingCartBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.customerorderlist.CustomerOrderListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart) {

    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var adapter: ShoppingCartAdapter
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

        binding.buttonOrder.setOnClickListener {
            val items = getShoppingList()
            val itemMapList = mutableListOf<HashMap<String, String>>()

            for (item in items) {
                val itemMap = hashMapOf<String, String>()
                itemMap["itemId"] = item.item.itemId.toString()
                itemMap["quantity"] = item.count.toString()
                itemMapList.add(itemMap)
            }


            if (items.size > 0) {
                val truckId = items[0].item.truckId
                val order = Order(
                    0,
                    SharedPrefs.getUserInfo()?.userId ?: 0,
                    truckId,
                    SharedPrefs.getTruckName() ?: "",
                    SharedPrefs.getUserInfo()?.username ?: "",
                    binding.editOtherRequest.text.toString(),
                    1000,
                    "",
                    OrderStatus.RECEIVED,
                    itemMapList
                )
                viewModel.insertOrder(order)
            } else {
                showToast("장바구니가 비어있습니다.")
            }
        }

        binding.editTruckTitle.isEnabled = false
        binding.editTruckTitle.setText(SharedPrefs.getTruckName())
    }

    private fun initRecyclerView() {
        val list = getShoppingList()
        adapter = ShoppingCartAdapter(list) { orderDetail ->
            adapter.removeItem(orderDetail)
        }
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }


    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@ShoppingCartActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@ShoppingCartActivity)
            isLoading.observe(this@ShoppingCartActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            orderId.observe(this@ShoppingCartActivity) {
                if(it != 0) {
                    showToast("주문을 성공했습니다.")
                    SharedPrefs.clearShoppingList()
                    val intent = Intent(this@ShoppingCartActivity, CustomerOrderListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}