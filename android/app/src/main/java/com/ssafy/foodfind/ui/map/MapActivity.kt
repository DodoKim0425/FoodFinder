package com.ssafy.foodfind.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
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
    val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var locationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location = Location("")
    private var isAlreadyPoped: Boolean = false
    private val UPDATE_INTERVAL = 1000 // 1초
    private val FASTEST_UPDATE_INTERVAL = 500 // 0.5초
    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMap()
        observeData()

        locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL.toLong()
            smallestDisplacement = 10.0f
            fastestInterval = FASTEST_UPDATE_INTERVAL.toLong()

        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initMap() {
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
        this.naverMap = p0
        viewModel.getAllTruckLocation()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            // 권한이 있으면 내 위치 조회
            getLocation()
        }
    }

    private fun addMarkers(item: TruckLocation) {
        if(item.location != "") {
            val marker = Marker()
            marker.position = LatLng(item.latitude, item.longitude)
            marker.map = naverMap
            marker.onClickListener = this
            marker.tag = item.truckId
        }
    }

    private fun setPosition(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition(
            LatLng(latitude, longitude),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
    }

    private fun startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            showToast("GPS 기능을 켜고 다시 시도해 주세요")
            finish()
        } else {
            if (checkRunTimePermission()) {
                mFusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (checkRunTimePermission() == false) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        } else {
            startLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d(TAG, "onStop: ")
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                userLocation = location
            }
        }
    }

    private fun checkRunTimePermission(): Boolean {
        if (applicationContext != null) {
            for (permission in REQUIRED_PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun checkLocationServicesStatus(): Boolean { //gps 켜져있는지 확인
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }
    override fun onClick(p0: Overlay): Boolean {
        viewModel.getTruckInfo(p0.tag.toString().toInt())
        return true
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // 내 위치를 사용하여 원하는 작업을 수행합니다.
                    setPosition(latitude, longitude)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                // 권한이 거부되었을 때 처리할 작업 수행
            }
        }
    }

}