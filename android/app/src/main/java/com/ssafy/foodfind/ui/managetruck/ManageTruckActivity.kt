package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.databinding.ActivityManageTruckBinding
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

private const val TAG = "ManageTruckActivity_싸피"
@AndroidEntryPoint
class ManageTruckActivity :
    BaseActivity<ActivityManageTruckBinding>(R.layout.activity_manage_truck) {
    private val viewModel by viewModels<TruckViewModel>()
    private var list = mutableListOf<FoodItem>()
    private var truckInfo = Truck()
    private lateinit var foodTruckAdapter: FoodTruckAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        truckInfo= intent.getSerializableExtra("truckInfo") as Truck
        binding.truck = truckInfo
        var test : ArrayList<FoodItem> = intent.getSerializableExtra("foodItemList") as ArrayList<FoodItem>
        test.map {
            list.add(it)
        }

        initRecyclerView()
        initButton()
    }

    private fun initRecyclerView(){
        foodTruckAdapter = FoodTruckAdapter(list)
        binding.rvMember.adapter=foodTruckAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
    }
    private fun initButton() {
        binding.btnCreateOk.setOnClickListener {
            viewModel.updateTruck(truckInfo)
            finish()
        }

        binding.btnDeleteOk.setOnClickListener {
            val intent = Intent(this, ManageTruckActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton.setOnClickListener {
            var bottomSheet = ManageTruckItemBottomSheet(this)
            bottomSheet.show()
        }
    }
}