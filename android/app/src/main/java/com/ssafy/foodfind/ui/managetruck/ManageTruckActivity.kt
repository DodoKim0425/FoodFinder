package com.ssafy.foodfind.ui.managetruck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.*
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.entity.TruckStatus
import com.ssafy.foodfind.databinding.ActivityManageTruckBinding
import com.ssafy.foodfind.databinding.ActivityManageTruckItemBinding
import com.ssafy.foodfind.databinding.ItemFoodBinding
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
    private var deletedList = mutableListOf<FoodItem>()

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
        foodItemUpdateAdapter.submitList(list.toMutableList())

        foodItemUpdateAdapter.itemClickListener = object : FoodItemUpdateAdapter.ItemClickListener{
            override fun onClick(data: FoodItem, position: Int) {
                var bottomSheet = UpdateTruckItemBottomSheet(this@ManageTruckActivity, data)
                bottomSheet.listener=object : UpdateTruckItemBottomSheet.OnSendFromBottomSheetDialog{
                    override fun sendValue(value: FoodItem, oldItem: FoodItem) {
                        list.forEachIndexed { index, it ->
                            if(it == oldItem) {
                                var originalItem = it.copy()
                                originalItem.apply {
                                    this.name=value.name
                                    this.description=value.description
                                    this.price=value.price
                                    this.status=value.status
                                }
                                list[index]=originalItem
                                foodItemUpdateAdapter.submitList(list.toMutableList())
                                return@forEachIndexed
                            }
                        }
                    }

                }
                bottomSheet.show()
            }
        }

        binding.rvMember.adapter= foodItemUpdateAdapter
        binding.rvMember.layoutManager = LinearLayoutManager(this)
        val itemTouchCallback = ItemTouchHelper(FoodItemTouchCallback(this, binding.rvMember))
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
                list.map {
                    if(it.truckId==0){//새로 추가되어야 하는 foodItem
                        viewModel.insertFoodItem(it, truckInfo.truckId)
                    }else{//수정되어야 하는 foodItem
                        viewModel.updateFoodItem(it)
                    }
                }
                deletedList.map {
                    if(it.truckId!=0){
                        viewModel.deleteFoodItem(it.itemId)
                    }
                }
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
                    list.add(value)
                    foodItemUpdateAdapter.submitList(list.toMutableList())
                }

            }
            bottomSheet.show()
        }
    }
    private fun observeData(){
        viewModel.newTruckId.observe(this@ManageTruckActivity){truckId ->
            list.map {
                Log.d(TAG, "observeData: insert!! $it")
                viewModel.insertFoodItem(it, truckId)
            }
        }
    }

    fun removeItem(position: Int){
            val newList=list
            deletedList.add(newList[position])
            newList.removeAt(position)
            foodItemUpdateAdapter.submitList(newList.toMutableList())

    }

}