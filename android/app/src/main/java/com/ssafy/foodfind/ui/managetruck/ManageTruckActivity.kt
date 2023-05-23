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
import com.ssafy.foodfind.SharedPrefs
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
    private var registMode = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setData()
        initRecyclerView()
        initButton()
    }

    private fun setData(){
        truckInfo.apply {
            if(intent.getSerializableExtra("truckInfo")==null){
                truckInfo= Truck()
            }else{
                truckInfo= intent.getSerializableExtra("truckInfo") as Truck
                registMode=false
            }
        }
        if(intent.getSerializableExtra("foodItemList")!=null){
            var foodItemList : ArrayList<FoodItem> = intent.getSerializableExtra("foodItemList") as ArrayList<FoodItem>
            foodItemList.map {
                list.add(it)
            }
        }
        binding.truck = truckInfo


    }

    private fun initRecyclerView(){
        foodTruckAdapter = FoodTruckAdapter(list)
        binding.rvMember.adapter=foodTruckAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
    }
    private fun initButton() {
        binding.btnCreateOk.setOnClickListener {
            if(registMode){
                if (SharedPrefs.getUserInfo() != null) {
                    truckInfo.apply {
                        ownerId = SharedPrefs.getUserInfo()!!.userId
                        location = "37.5512/126.9882"
                        currentStatus = "CLOSED"
                    }
                    viewModel.registTruck(truckInfo)
                }

            }else{
                viewModel.updateTruck(truckInfo)
            }
            finish()
        }

        binding.btnDeleteOk.setOnClickListener {
            val intent = Intent(this, ManageTruckActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton.setOnClickListener {
            var bottomSheet = ManageTruckItemBottomSheet(this)
            bottomSheet.listener=object : ManageTruckItemBottomSheet.OnSendFromBottomSheetDialog{
                override fun sendValue(value: FoodItem) {
                    Log.d(TAG, "sendValue: $value")
                }

            }
            bottomSheet.show()
        }
    }
}