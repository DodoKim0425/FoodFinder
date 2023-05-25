package com.ssafy.foodfind.ui.truckinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.data.entity.*
import com.ssafy.foodfind.databinding.ActivityTruckInfoBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.managetruck.TruckViewModel
import com.ssafy.foodfind.ui.managetruck.UpdateTruckItemBottomSheet
import com.ssafy.foodfind.ui.shoppingcart.ShoppingCartActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TruckInfoActivity_싸피"
@AndroidEntryPoint
class TruckInfoActivity : BaseActivity<ActivityTruckInfoBinding>(R.layout.activity_truck_info),
    OnMapReadyCallback {
    private val viewModel by viewModels<TruckViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var list : MutableList<Comment>
    private lateinit var commentAdapter: TruckCommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list= mutableListOf()

        initButton()
        initTruck()
        observeData()
        initRecyclerView()
    }

    private fun initTruck() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    fun initRecyclerView(){
        if (SharedPrefs.getUserInfo() != null) {
            val userId = SharedPrefs.getUserInfo()!!.userId

            commentAdapter = TruckCommentAdapter(list, userId)
            commentAdapter.clickListener=object :TruckCommentAdapter.ClickListener{
                override fun onDeleteClicked(comment: Comment) {
                    var idx = -1
                    list.forEachIndexed{ index, oriComment ->
                        if(comment==oriComment){
                            idx=index
                            return@forEachIndexed
                        }
                    }
                    if(idx!=-1){
                        viewModel.deleteComment(comment.commentId)
                        list.removeAt(idx)
                        commentAdapter.notifyDataSetChanged()
                    }

                }

                override fun onUpdateClicked(comment: Comment) {
                    var bottomSheet = UpdateCommentBottomSheet(this@TruckInfoActivity, comment)
                    bottomSheet.listener=object : UpdateCommentBottomSheet.OnSendFromBottomSheetDialog{
                        override fun sendValue(value: Comment) {
                            list.forEachIndexed { index, comment ->
                                if(comment.commentId==value.commentId){
                                    list[index]=value
                                    commentAdapter.notifyDataSetChanged()
                                    return@forEachIndexed
                                }
                            }
                            viewModel.updateComment(value)
                        }

                    }
                    bottomSheet.show()
                }

            }

            binding.rvComment.adapter=commentAdapter
            binding.rvComment.layoutManager=LinearLayoutManager(this)
        }


    }

    private fun initComments(truckId:Int){
        viewModel.getCommentList(truckId)
    }
    private fun initFood(items: List<FoodItem>) {
        val adapter = FoodAdapter(items)
        binding.rvFood.adapter = adapter

        adapter.setItemClickListener { item ->
            val orderDetail = OrderDetail(item, 0)
            if(binding.truck != null) {
                val bottomSheet = OrderDetailBottomSheet.newInstance(orderDetail, binding.truck!!)
                bottomSheet.show(supportFragmentManager, "order_detail_bottom_sheet")
            }
        }
    }

    private fun initButton() {
        binding.btnShoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@TruckInfoActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@TruckInfoActivity)
            isLoading.observe(this@TruckInfoActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            truck.observe(this@TruckInfoActivity) { truck ->
                binding.truck = truck
                val location = TruckLocation(truck.truckId, truck.location)
                addMarker(location)
                setPosition(location)
                initComments(truck.truckId)
            }

            truckItems.observe(this@TruckInfoActivity) {
                initFood(it)
            }

            commentList.observe(this@TruckInfoActivity){
                list.clear()
                list.addAll(it)
                commentAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0

        val truckId = intent.getIntExtra("truckId", -1)
        if (truckId != -1) {
            viewModel.getTruckInfo(truckId)
            viewModel.getTruckItemInfo(truckId)
        }
    }

    private fun setPosition(item: TruckLocation) {
        val cameraPosition = CameraPosition(
            LatLng(item.latitude, item.longitude),
            14.0
        )
        naverMap.cameraPosition = cameraPosition
    }

    private fun addMarker(item: TruckLocation) {
        val marker = Marker()
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
    }
}