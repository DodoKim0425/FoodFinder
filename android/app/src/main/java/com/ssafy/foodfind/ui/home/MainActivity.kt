package com.ssafy.foodfind.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.foodfind.R
import com.ssafy.foodfind.databinding.ActivityMainBinding
import com.ssafy.foodfind.ui.customerorderlist.CustomerOrderListActivity
import com.ssafy.foodfind.ui.managetruck.ManageTruckActivity
import com.ssafy.foodfind.ui.managetruck.ManageTruckItemActivity
import com.ssafy.foodfind.ui.map.MapActivity
import com.ssafy.foodfind.ui.notification.NotificationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentPage = 0
    private lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        initButton()
    }

    private fun initButton() {
        binding.btnDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
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
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
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
}