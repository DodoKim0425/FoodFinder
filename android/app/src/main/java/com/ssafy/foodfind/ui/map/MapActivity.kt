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
class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map),
    OnMapReadyCallback, Overlay.OnClickListener {
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        observeData()
    }

    private fun initButton() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)

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
                    addMarkers(document)
                }
            }

            truck.observe(this@MapActivity) {
                val truckBottomSheet = TruckBottomSheet(this@MapActivity, it)
                truckBottomSheet.show()
                val location = TruckLocation(it.truckId, it.location)
                setPosition(location.latitude, location.longitude)
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        Log.d(TAG, "onMapReady: ")
        this.naverMap = p0
        viewModel.getAllTruckLocation()
    }

    private fun addMarkers(item: TruckLocation) {
        val marker = Marker()
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
        marker.onClickListener = this
        marker.tag = item.truckId
    }

    private fun setPosition(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition(
            LatLng(latitude, longitude),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
    }

    override fun onClick(p0: Overlay): Boolean {
        viewModel.getTruckInfo(p0.tag.toString().toInt())
        return true
    }
}