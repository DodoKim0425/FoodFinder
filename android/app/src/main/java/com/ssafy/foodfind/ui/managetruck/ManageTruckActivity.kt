package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.entity.TruckStatus
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
    private var truckInfo = Truck(0, 0, "", 0.0F, "", "", TruckStatus.CLOSED)
    private lateinit var foodItemUpdateAdapter: FoodItemUpdateAdapter
    private var registMode = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setData()
        initRecyclerView()
        initButton()
        observeData()
    }

    private fun setData(){
        truckInfo.apply {
            if(intent.getSerializableExtra("truckInfo")==null){
                truckInfo= Truck(0, 0, "", 0.0F, "", "", TruckStatus.CLOSED)
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
        foodItemUpdateAdapter = FoodItemUpdateAdapter()
        foodItemUpdateAdapter.submitList(list)

        foodItemUpdateAdapter.itemClickListener = object : FoodItemUpdateAdapter.ItemClickListener{
            override fun onClick(data: FoodItem, position: Int) {
                var bottomSheet = UpdateTruckItemBottomSheet(this@ManageTruckActivity, data, position)
                bottomSheet.listener=object : UpdateTruckItemBottomSheet.OnSendFromBottomSheetDialog{
                    override fun sendValue(value: FoodItem, position:Int) {
                        var newList = foodItemUpdateAdapter.currentList.toMutableList()
                        newList[position]=value
                        foodItemUpdateAdapter.submitList(newList)
                    }

                }
                bottomSheet.show()
            }
        }

        binding.rvMember.adapter= foodItemUpdateAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
        val itemTouchCallback = ItemTouchHelper(FoodItemTouchCallback(binding.rvMember))
        itemTouchCallback.attachToRecyclerView(binding.rvMember)
    }
    private fun initButton() {
        binding.btnCreateOk.setOnClickListener {
            if(registMode){
                if (SharedPrefs.getUserInfo() != null) {
                    truckInfo.apply {
                        ownerId = SharedPrefs.getUserInfo()!!.userId
                        location = "37.5512/126.9882"
                        currentStatus = TruckStatus.CLOSED
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
                    var newList = foodItemUpdateAdapter.currentList.toMutableList()
                    newList.add(value)
                    foodItemUpdateAdapter.submitList(newList)
                }

            }
            bottomSheet.show()
        }
    }
    private fun observeData(){
        viewModel.newTruckId.observe(this@ManageTruckActivity){truckId ->
            val newList = foodItemUpdateAdapter.currentList
            newList.map {
                viewModel.insertFoodItem(it, truckId)
            }
        }
    }
}