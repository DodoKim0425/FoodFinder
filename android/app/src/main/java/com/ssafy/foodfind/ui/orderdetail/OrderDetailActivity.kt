package com.ssafy.foodfind.ui.orderdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.Comment
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.OrderStatus
import com.ssafy.foodfind.databinding.ActivityOrderDetailBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.shoppingcart.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "OrderDetailActivity_싸피"
@AndroidEntryPoint
class OrderDetailActivity :
    BaseActivity<ActivityOrderDetailBinding>(R.layout.activity_order_detail) {
    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var adapter: OrderDetailAdapter
    private var phoneNumber = ""
    private var truckId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOrder()
        initRecyclerView()
        observeData()
    }

    private fun initOrder() {
        val orderId = intent.getIntExtra("orderId", -1)
        if (orderId != -1) {
            viewModel.getDetailOrder(orderId)
            viewModel.selectPhoneInfo(orderId)
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.orderCancel.setOnClickListener {
            if (binding.order?.orderStatus == OrderStatus.RECEIVED) {
                viewModel.updateOrderToCancel(orderId)
            }
        }
        binding.orderStart.setOnClickListener {
            viewModel.selectOrder(orderId)
        }
        binding.orderCall.setOnClickListener {
            startDialer(phoneNumber)
        }
        binding.orderComment.setOnClickListener {
            var bottomSheet : CommentInsertBottomSheet= CommentInsertBottomSheet(this@OrderDetailActivity)
            bottomSheet.listener=object :CommentInsertBottomSheet.OnSendFromBottomSheetDialog{
                override fun sendValue(value: Comment) {
                    var newComment=value.copy()
                    newComment.apply {
                        if(truckId!=-1 && SharedPrefs.getUserInfo()!=null){
                            userId=SharedPrefs.getUserInfo()!!.userId
                            truckId=truckId
                        }
                    }
                    if(newComment.userId!=0){
                        viewModel.insertComment(newComment)
                        showToast("댓글이 등록 되었습니다")
                    }
                }

            }
            bottomSheet.show()
        }
    }

    private fun initRecyclerView() {
        adapter = OrderDetailAdapter()
        binding.recyclerViewOrderItems.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerViewOrderItems.adapter = adapter
    }


    private fun startDialer(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(dialIntent)
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
                if (it.isNotEmpty()) {
                    val order = it[0]
                    binding.order = order
                    //truckId=order.truckId
                    if ((SharedPrefs.getUserInfo()?.userId ?: -1) == order.userId) {
                        binding.orderStart.visibility = View.GONE
                    } else {
                        binding.orderComment.visibility = View.GONE
                    }
                    if (order.orderStatus != OrderStatus.RECEIVED) {
                        binding.orderCancel.isEnabled = false
                    }
                    if (order.orderStatus == OrderStatus.RECEIVED) {
                        binding.orderStart.isEnabled = false
                    }
                    if (order.orderStatus == OrderStatus.CANCEL) {
                        binding.orderStart.text = "취소된 주문"
                        binding.orderComment.isEnabled = false
                        binding.orderStart.isEnabled = false
                    }
                    if (order.orderStatus == OrderStatus.DONE) {
                        binding.orderStart.text = "조리 완료"
                        binding.orderStart.isEnabled = false
                    }
                    if (order.orderStatus == OrderStatus.COOKING) {
                        binding.orderStart.text = "조리 완료"
                        binding.orderComment.isEnabled = false
                    }
                    adapter.addOrderDetail(it)
                }
            }

            isCancel.observe(this@OrderDetailActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        showToast("주문이 취소되었습니다.")
                        finish()
                    }
                }
            }

            order.observe(this@OrderDetailActivity) {
                val order: Order
                truckId=it.truckId
                Log.d(TAG, "observeData: --------------$it")
                if (it.orderStatus == OrderStatus.RECEIVED) {
                    order = it.apply {
                        orderStatus = OrderStatus.COOKING
                    }
                    viewModel.updateOrder(order)
                } else if (it.orderStatus == OrderStatus.COOKING) {
                    order = it.apply {
                        orderStatus = OrderStatus.DONE
                    }
                    viewModel.updateOrder(order)
                }
            }

            orderPhoneInfo.observe(this@OrderDetailActivity) {
                phoneNumber = if ((SharedPrefs.getUserInfo()?.userId ?: -1) == it.userId) {
                    it.ownerPhone
                } else {
                    it.customerPhone
                }
            }

            isChanged.observe(this@OrderDetailActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        showToast("변경되었습니다.")
                        viewModel.getDetailOrder(intent.getIntExtra("orderId", -1))
                    }
                }
            }
        }
    }
}