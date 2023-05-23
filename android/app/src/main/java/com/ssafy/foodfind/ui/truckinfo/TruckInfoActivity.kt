package com.ssafy.foodfind.ui.truckinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.TruckLocation
import com.ssafy.foodfind.databinding.ActivityTruckInfoBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.managetruck.TruckViewModel
import com.ssafy.foodfind.ui.map.MapFragmentWrapper
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckInfoActivity : BaseActivity<ActivityTruckInfoBinding>(R.layout.activity_truck_info) {
    private val viewModel by viewModels<TruckViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTruck()
        initButton()
        observeData()
    }

    private fun initTruck() {
        val truckId = intent.getIntExtra("truckId", -1)
        if(truckId != -1) {
            viewModel.getTruckInfo(truckId)
            viewModel.getTruckItemInfo(truckId)
        }
        supportFragmentManager.beginTransaction().replace(R.id.mapContainer, MapFragmentWrapper).commit()
    }


    private fun initFood(items: List<Food>) {
        val adapter = FoodAdapter(items)
        binding.rvFood.adapter = adapter
    }

    private fun initButton() {
        binding.btnShoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@TruckInfoActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@TruckInfoActivity)
            isLoading.observe(this@TruckInfoActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            truck.observe(this@TruckInfoActivity) { truck ->
                binding.truck = truck
                val location = TruckLocation(truck.truckId, truck.location)
                handleMapRelatedWork(location)
            }

            truckItems.observe(this@TruckInfoActivity) {
                initFood(it)
            }
        }
    }

    private fun handleMapRelatedWork(location: TruckLocation) {
        MapFragmentWrapper.addMarker(location)
        MapFragmentWrapper.setPosition(location)
    }

    override fun onDestroy() {
        super.onDestroy()

        val mapFragmentWrapper = supportFragmentManager.findFragmentById(R.id.mapContainer) as? MapFragmentWrapper
        if (mapFragmentWrapper != null) {
            supportFragmentManager.beginTransaction().remove(mapFragmentWrapper).commit()
        }
    }
}