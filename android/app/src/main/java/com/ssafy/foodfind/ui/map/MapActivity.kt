package com.ssafy.foodfind.ui.map

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.TruckLocation
import com.ssafy.foodfind.databinding.ActivityMapBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MapActivity_싸피"

@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map) {
    private val viewModel by viewModels<MapViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMap()
        observeData()
    }

    private fun initMap() {
        supportFragmentManager.beginTransaction().add(R.id.mapContainer, MapFragmentWrapper).commit()
        viewModel.getAllTruckLocation()
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@MapActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@MapActivity)
            isLoading.observe(this@MapActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            locations.observe(this@MapActivity) {
                for (document in it) {
                    MapFragmentWrapper.addMarker(document)
                }
            }

            truck.observe(this@MapActivity) {
                val truckBottomSheet = TruckBottomSheet(this@MapActivity, it)
                truckBottomSheet.show()
                val location = TruckLocation(it.truckId, it.location)
                MapFragmentWrapper.setPosition(location)
            }

            MapFragmentWrapper.setMarkerClickListener { truckId ->
                viewModel.getTruckInfo(truckId)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        val mapFragmentWrapper = supportFragmentManager.findFragmentById(R.id.mapContainer) as? MapFragmentWrapper
        if (mapFragmentWrapper != null) {
            supportFragmentManager.beginTransaction().remove(mapFragmentWrapper).commit()
        }
    }
}