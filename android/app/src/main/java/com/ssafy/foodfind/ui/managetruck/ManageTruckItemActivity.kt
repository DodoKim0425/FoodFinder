package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.FoodItem
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
    private lateinit var truckInfo : Truck
    private lateinit var foodTruckAdapter: FoodTruckAdapter
    private var list : MutableList<FoodItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        initButton()
        initTruck()
        observeData()
    }

    private fun initRecyclerView(){
        foodTruckAdapter = FoodTruckAdapter(list)
        binding.rvMember.adapter=foodTruckAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
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
            truckInfo.apply {
                when(binding.radioGroupStatus.checkedRadioButtonId){
                    R.id.truck_open -> this.currentStatus="OPEN"
                    else -> this.currentStatus ="CLOSED"
                }
            }
            viewModel.updateTruck(truckInfo)
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
                        truckInfo = truck
                        viewModel.getFoodItem(truck.truckId)
                    }
                }
            }

            foodItemList.observe(this@ManageTruckItemActivity){ event ->
                event.getContentIfNotHandled()?.let {
                    Log.d(TAG, "observeData: ${it.size}")
                    list.clear()
                    list.addAll(it)
                    foodTruckAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}