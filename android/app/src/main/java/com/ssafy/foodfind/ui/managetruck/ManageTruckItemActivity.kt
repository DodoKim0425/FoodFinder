package com.ssafy.foodfind.ui.managetruck

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
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
import com.ssafy.foodfind.data.entity.TruckStatus
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ManageTruckItemActivity"

@AndroidEntryPoint
class ManageTruckItemActivity :
    BaseActivity<ActivityManageTruckItemBinding>(R.layout.activity_manage_truck_item),
    OnMapReadyCallback {
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val PERMISSIONS_REQUEST_CODE = 100
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val UPDATE_INTERVAL = 1000 // 1초
    private val FASTEST_UPDATE_INTERVAL = 500 // 0.5초
    private val viewModel by viewModels<TruckViewModel>()
    private var truckInfo: Truck = Truck(0, 0, "", 0.0F, "", "", TruckStatus.CLOSED)
    private lateinit var foodTruckAdapter: FoodTruckAdapter
    private var list: MutableList<FoodItem> = mutableListOf()
    private var marker = Marker()
    private lateinit var naverMap: NaverMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location = Location("")
    private var isAlreadyPoped: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        binding.truck = Truck(0, 0, "", 0.0F, "", "", TruckStatus.CLOSED)


        locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL.toLong()
            smallestDisplacement = 10.0f
            fastestInterval = FASTEST_UPDATE_INTERVAL.toLong()

        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        initRecyclerView()
        observeData()
    }

    private fun startLocationUpdates() {
        // 위치서비스 활성화 여부 check
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

    private fun initRecyclerView() {
        foodTruckAdapter = FoodTruckAdapter(list)
        binding.rvFood.adapter = foodTruckAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(this)
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
            var foodItemList: ArrayList<FoodItem> = ArrayList<FoodItem>()
            foodItemList.addAll(list)
            intent.putExtra("foodItemList", foodItemList)
            startActivity(intent)
        }

        binding.radioGroupStatus.setOnCheckedChangeListener { group, checkedId ->
            truckInfo.apply {
                when (binding.radioGroupStatus.checkedRadioButtonId) {
                    R.id.truck_open -> this.currentStatus = TruckStatus.OPEN
                    else -> this.currentStatus = TruckStatus.CLOSED
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            val isGPSOn = checkLocationServicesStatus()
            if (isGPSOn == false) {
                showToast("GPS를 켜고 다시 시도해주세요")
            } else if (checkRunTimePermission() == false) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                getLocation()
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

            truck.observe(this@ManageTruckItemActivity) { truck ->
                if (truck.truckId == 0) {
                    Log.d(TAG, "observeData: $truck")
                    //다이얼로그 띄우기
                    if (isAlreadyPoped == false) {
                        val intent =
                            Intent(this@ManageTruckItemActivity, ManageTruckActivity::class.java)
                        startActivity(intent)
                        finish()
                        isAlreadyPoped = true
                    }

                } else {
                    Log.d(TAG, "observeData: $truck")
                    binding.truck = truck
                    truckInfo = truck
                    viewModel.getFoodItem(truck.truckId)
                    setMarker(truck.location)
                    SharedPrefs.saveTruckId(truckId = truck.truckId)
                    binding.truck = truck
                }
            }


            foodItemList.observe(this@ManageTruckItemActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    Log.d(TAG, "observeData: ${it.size}")
                    list.clear()
                    list.addAll(it)
                    foodTruckAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    fun setMarker(locationString: String) {
        if (locationString != "") {
            Log.d(TAG, "setMarker: ${locationString}")
            var latLngArr = locationString.split("/").map {
                it.toDouble()
            }
            marker.position = LatLng(latLngArr[0], latLngArr[1])
            marker.map = naverMap
            setPosition(latLngArr[0], latLngArr[1])
        }

    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        initTruck()
        initButton()
    }

    fun setPosition(y: Double, x: Double) {
        val cameraPosition = CameraPosition(
            LatLng(y, x),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
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

    private fun getLocation() {
        if (userLocation.latitude != null && userLocation.longitude != null) {
            setMarker("${userLocation.latitude}/${userLocation.longitude}")
            truckInfo.location = "${userLocation.latitude}/${userLocation.longitude}"
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (!(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
                    alertDialog.setTitle("위치 권한이 필요합니다.")
                    alertDialog.setMessage("위치 권한 설정을 위해 설정으로 이동합니다.")
                    alertDialog.setPositiveButton("확인") { dialogInterface, i -> // 안드로이드 버전에 따라 다를 수 있음.
                        val intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + this.packageName))
                        this.startActivity(intent)
                        dialogInterface.cancel()
                    }
                    alertDialog.setOnDismissListener {
                        finish()
                    }
                    alertDialog.show()
                } else {
                    startLocationUpdates()
                }
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

    override fun onResume() {
        super.onResume()
        initTruck()
    }
}