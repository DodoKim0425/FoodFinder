package com.ssafy.foodfind.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.foodfind.R
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var currentPage = 0
    private lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = BannerAdapter()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        viewPager.setPageTransformer(compositePageTransformer)
        viewPager.offscreenPageLimit = 3
        viewPager.setCurrentItem(1, false)
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
                    if (currentPage == viewPager.adapter?.itemCount ?: 0) {
                        currentPage = 0
                    }
                    viewPager.setCurrentItem(currentPage++, true)
                }
            }
        }, 3000, 3000) // 3초마다 슬라이드되도록 설정 (원하는 대로 수정 가능)
    }

    private fun stopAutoScroll() {
        timer.cancel()
    }
}