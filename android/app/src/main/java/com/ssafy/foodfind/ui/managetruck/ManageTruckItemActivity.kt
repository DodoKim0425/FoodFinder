package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.ActivityCustomerOrderListBinding
import com.ssafy.foodfind.databinding.ActivityLoginBinding
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.home.MainActivity
import com.ssafy.foodfind.ui.login.LoginViewModel
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ManageTruckItemActivity"

@AndroidEntryPoint
class ManageTruckItemActivity :
    BaseActivity<ActivityManageTruckItemBinding>(R.layout.activity_manage_truck_item) {

    private val viewModel by viewModels<TruckViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        initTruck()
        observeData()
    }

    private fun initTruck() {
        binding.truck = Truck()
        if (SharedPrefs.getUserInfo() != null) {
            val userId = SharedPrefs.getUserInfo()!!.userId
            viewModel.getMyTruckInfo(userId)
        }
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnUpdateTruck.setOnClickListener {
            val intent = Intent(this, ManageTruckActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@ManageTruckItemActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@ManageTruckItemActivity)
            isLoading.observe(this@ManageTruckItemActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            truck.observe(this@ManageTruckItemActivity) { event ->
                event.getContentIfNotHandled()?.let { truck ->
                    Log.d(TAG, "observeData: ")
                    if (truck.truckId == 0) {
                        //다이얼로그 띄우기
                        val intent =
                            Intent(this@ManageTruckItemActivity, ManageTruckActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d(TAG, "observeData: ")
                        binding.truck = truck
                    }
                }
            }
        }
    }
}