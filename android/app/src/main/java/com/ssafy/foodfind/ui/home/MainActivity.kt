package com.ssafy.foodfind.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.databinding.ActivityMainBinding
import com.ssafy.foodfind.ui.customerorderlist.CustomerOrderListActivity
import com.ssafy.foodfind.ui.managetruck.ManageTruckItemActivity
import com.ssafy.foodfind.ui.map.MapActivity
import com.ssafy.foodfind.ui.notification.NotificationActivity
import com.ssafy.foodfind.ui.ownerorderlist.OwnerOrderListActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: Timer
    private lateinit var user: User
    private var currentPage = 0
    val PERMISSIONS_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        initButton()
        initUser()

        if (!checkRunTimePermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun initButton() {
        binding.btnDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    finish()
                    true
                }
                // 다른 메뉴 아이템에 대한 처리
                else -> false
            }
        }

        binding.findStore.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        binding.manageOrder.setOnClickListener {
            val intent = Intent(this, CustomerOrderListActivity::class.java)
            startActivity(intent)
        }

        binding.manageStore.setOnClickListener {
            val intent = Intent(this, ManageTruckItemActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotification.setOnClickListener {
            val intent = Intent(this, OwnerOrderListActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initUser() {
        if(SharedPrefs.getUserInfo() != null) {
            user = SharedPrefs.getUserInfo()!!
            binding.username.text = user.username
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = BannerAdapter()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.viewPager.setPageTransformer(compositePageTransformer)
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.setCurrentItem(1, false)
        startAutoScroll()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoScroll()
    }

    private fun startAutoScroll() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (currentPage == (binding.viewPager.adapter?.itemCount ?: 0)) {
                        currentPage = 0
                    }
                    binding.viewPager.setCurrentItem(currentPage++, true)
                }
            }
        }, 5000, 5000)
    }

    private fun stopAutoScroll() {
        timer.cancel()
    }

    private fun checkRunTimePermission(): Boolean {
        val requiredPermissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        for (permission in requiredPermissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}