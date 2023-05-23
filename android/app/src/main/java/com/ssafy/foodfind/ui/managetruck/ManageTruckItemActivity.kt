package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ManageTruckItemActivity"

@AndroidEntryPoint
class ManageTruckItemActivity :
    BaseActivity<ActivityManageTruckItemBinding>(R.layout.activity_manage_truck_item), OnMapReadyCallback {

    private val viewModel by viewModels<TruckViewModel>()
    private var truckInfo : Truck = Truck()
    private lateinit var foodTruckAdapter: FoodTruckAdapter
    private var list : MutableList<FoodItem> = mutableListOf()
    private var marker = Marker()
    private lateinit var naverMap : NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        binding.truck = Truck()

        initRecyclerView()
        initButton()
        observeData()

    }

    private fun initRecyclerView(){
        foodTruckAdapter = FoodTruckAdapter(list)
        binding.rvMember.adapter=foodTruckAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
    }

    private fun initTruck() {

        if (SharedPrefs.getUserInfo() != null) {
            val userId = SharedPrefs.getUserInfo()!!.userId
            viewModel.getMyTruckInfo(userId)
        }
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            viewModel.updateTruck(truckInfo)
            finish()
        }

        binding.btnUpdateTruck.setOnClickListener {
            val intent = Intent(this, ManageTruckActivity::class.java)
            intent.putExtra("truckInfo", truckInfo)
            var foodItemList : ArrayList<FoodItem> = ArrayList<FoodItem>()
            foodItemList.addAll(list)
            intent.putExtra("foodItemList", foodItemList)
            startActivity(intent)
        }

        binding.radioGroupStatus.setOnCheckedChangeListener { group, checkedId ->
            truckInfo.apply {
                when(binding.radioGroupStatus.checkedRadioButtonId){
                    R.id.truck_open -> this.currentStatus="OPEN"
                    else -> this.currentStatus ="CLOSED"
                }
            }
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
                        setMarker(truck.location)
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

    fun setMarker(locationString : String){
        Log.d(TAG, "setMarker: ${locationString}")
        var latLngArr = locationString.split("/").map {
            it.toDouble()
        }
        marker.position = LatLng(latLngArr[0], latLngArr[1])
        marker.map = naverMap
        setPosition(latLngArr[0], latLngArr[1])
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        naverMap=p0
        initTruck()
    }

    fun setPosition(y: Double, x: Double) {
        val cameraPosition = CameraPosition(
            LatLng(y, x),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
    }
}