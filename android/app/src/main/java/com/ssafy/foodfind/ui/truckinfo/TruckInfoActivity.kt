package com.ssafy.foodfind.ui.truckinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.TruckLocation
import com.ssafy.foodfind.databinding.ActivityTruckInfoBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.managetruck.TruckViewModel
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckInfoActivity : BaseActivity<ActivityTruckInfoBinding>(R.layout.activity_truck_info),
    OnMapReadyCallback {
    private val viewModel by viewModels<TruckViewModel>()
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        initTruck()
        observeData()
    }

    private fun initTruck() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
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
                addMarker(location)
                setPosition(location)
            }

            truckItems.observe(this@TruckInfoActivity) {
                initFood(it)
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0

        val truckId = intent.getIntExtra("truckId", -1)
        if (truckId != -1) {
            viewModel.getTruckInfo(truckId)
            viewModel.getTruckItemInfo(truckId)
        }
    }

    private fun setPosition(item: TruckLocation) {
        val cameraPosition = CameraPosition(
            LatLng(item.latitude, item.longitude),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
    }

    private fun addMarker(item: TruckLocation) {
        val marker = Marker()
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
    }
}