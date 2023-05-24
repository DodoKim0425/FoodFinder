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
        foodItemUpdateAdapter = FoodItemUpdateAdapter(list)

        foodItemUpdateAdapter.itemClickListener = object : FoodItemUpdateAdapter.ItemClickListener{
            override fun onClick(data: FoodItem, position: Int) {
                var bottomSheet = UpdateTruckItemBottomSheet(this@ManageTruckActivity, data)
                bottomSheet.listener=object : UpdateTruckItemBottomSheet.OnSendFromBottomSheetDialog{
                    override fun sendValue(value: FoodItem) {
                        //var newList = list
                        var originalItem = data.copy()
                        originalItem.apply {
                            this.name=value.name
                            this.description=value.description
                            this.price=value.price
                            this.status=value.status
                        }

                        list[position]=originalItem
                        foodItemUpdateAdapter.notifyDataSetChanged()
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
                val newList=list
                newList.map {
                    if(it.truckId==0){//새로 추가되어야 하는 foodItem
                        viewModel.insertFoodItem(it, truckInfo.truckId)
                    }else{//수정되어야 하는 foodItem
                        viewModel.updateFoodItem(it)
                    }
                }
                val deletedList = foodItemUpdateAdapter.getDeletedList()
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
//                    var newList = foodItemUpdateAdapter.currentList.toMutableList()
                    list.add(value)
                    //foodItemUpdateAdapter.submitList(list.toMutableList())
                    foodItemUpdateAdapter.notifyDataSetChanged()
                }

            }
            bottomSheet.show()
        }
    }
    private fun observeData(){
        viewModel.newTruckId.observe(this@ManageTruckActivity){truckId ->
            val newList = list
            newList.map {
                viewModel.insertFoodItem(it, truckId)
            }
        }
    }




    companion object itemComparator: DiffUtil.ItemCallback<FoodItem>(){
        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }
        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            Log.d(TAG, "areContentsTheSame: $oldItem")
            Log.d(TAG, "areContentsTheSame: $newItem")
            return oldItem==newItem
        }
    }

    class FoodItemUpdateAdapter (private val foodItemList : MutableList<FoodItem>): RecyclerView.Adapter<FoodItemUpdateAdapter.CustomViewHolder>(){

        private var deletedList= mutableListOf<FoodItem>()
        inner class CustomViewHolder(private val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root){
            fun bindInfo(data:FoodItem, position: Int){
                Log.d(TAG, "bindInfo: biding!!! $data")
                binding.food=data
                binding.root.setOnClickListener {
                    itemClickListener.onClick(data, position)
                }
            }
            fun setBackgound(color:Int){
                itemView.setBackgroundColor(color)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemFoodBinding.inflate(inflater, parent, false)
            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return foodItemList.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.apply{
                bindInfo(foodItemList[position], position)
            }
        }


        interface ItemClickListener{
            fun onClick(data:FoodItem, position: Int)
        }
        lateinit var itemClickListener: ItemClickListener

        fun getDeletedList() : MutableList<FoodItem>{
            return deletedList
        }

        fun removeItem(position: Int){
//            val newList=list
//            deletedList.add(newList[position])
//            newList.removeAt(position)
//            Log.d(TAG, "removeItem: ------------${newList.size}")
//            foodItemUpdateAdapter.submitList(newList.toMutableList())
            foodItemList.removeAt(position)
            notifyItemRemoved(position)
        }

    }
}