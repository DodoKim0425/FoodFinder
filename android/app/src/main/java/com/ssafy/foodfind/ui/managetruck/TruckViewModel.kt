package com.ssafy.foodfind.ui.managetruck

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.ssafy.foodfind.data.entity.*
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.comment.CommentRepository
import com.ssafy.foodfind.data.repository.foodItem.FoodItemRepository
import com.ssafy.foodfind.data.repository.truck.TruckRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "TruckViewModel_싸피"

@HiltViewModel
class TruckViewModel @Inject constructor(
    private val truckRepository: TruckRepository,
    private val foodItemRepository: FoodItemRepository,
    private val commentRepository: CommentRepository
    ) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _truck = MutableLiveData<Truck>()
    val truck: LiveData<Truck> = _truck

    private val _foodItemList = MutableLiveData<Event<List<FoodItem>>>()
    val foodItemList: LiveData<Event<List<FoodItem>>> = _foodItemList

    private val _newTruckId = MutableLiveData<Int>()
    val newTruckId: LiveData<Int> = _newTruckId

    private val _truckItem = MutableLiveData<List<FoodItem>>()
    val truckItems: LiveData<List<FoodItem>> = _truckItem

    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList : LiveData<List<Comment>> = _commentList

    fun getMyTruckInfo(ownerId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.getMyTruckInfoRequest(ownerId)

            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _truck.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }


    fun getTruckInfo(truckId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.getTruckRequest(truckId)
            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _truck.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }


    fun getTruckItemInfo(truckId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.getTruckItemRequest(truckId)
            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _truckItem.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun getFoodItem(truckId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = foodItemRepository.getFoodItemResponseByTruckId(truckId)
            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _foodItemList.postValue(Event(response.body))
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun updateTruck(truck: Truck) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.updateTruckRequest(truck)
            val type = "업데이트에"
            when (response) {
                is NetworkResponse.Success -> {
                    //_newTruckId.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun registTruck(truck: Truck) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.insertTruckRequest(truck)
            val type = "추가에"
            when (response) {
                is NetworkResponse.Success -> {
                    _newTruckId.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun insertFoodItem(foodItem : FoodItem, truckId: Int){
        Log.d(TAG, "insertFoodItem: $foodItem")
        foodItem.truckId=truckId
        showProgress()
        viewModelScope.launch{
            Log.d(TAG, "insertFoodItem: 변경 ${foodItem}")
            val response = foodItemRepository.insertFoodItemResponse(foodItem)
            Log.d(TAG, "insertFoodItem: response ${response}")
            val type = "추가에"
            when(response){
                is NetworkResponse.Success -> {

                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun insertFoodItemList(foodItems:List<FoodItem>){
        showProgress()
        viewModelScope.launch {
            val response = foodItemRepository.insertAllFoodItemsResponse(foodItems)
            val type = "추가에"
            when(response){
                is NetworkResponse.Success -> {

                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()

        }
    }

    fun updateFoodItem(foodItem: FoodItem){
        showProgress()
        viewModelScope.launch {
            val response = foodItemRepository.updateFoodItemResponse(foodItem)
            val type = "수정에"
            when(response){
                is NetworkResponse.Success -> {

                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun deleteFoodItem(foodItemId : Int){
        showProgress()
        viewModelScope.launch {
            val response = foodItemRepository.updateFoodItemToNotUseResponse(foodItemId)
            val type = "삭제에"
            when(response){
                is NetworkResponse.Success -> {

                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }


    fun getCommentList(truckId : Int){
        showProgress()
        viewModelScope.launch {
            val response = commentRepository.getCommentByTruckResponse(truckId)
            val type = "불러오기에"
            when(response){
                is NetworkResponse.Success -> {
                    _commentList.postValue(response.body)
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun deleteComment(commentId: Int){
        showProgress()
        viewModelScope.launch {
            val response = commentRepository.deleteCommentResponse(commentId)
            val type = "삭제에"
            when(response){
                is NetworkResponse.Success -> {
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun updateComment(comment: Comment){
        showProgress()
        viewModelScope.launch {
            val response = commentRepository.updateCommentResponse(comment)
            val type = "수정에"
            when(response){
                is NetworkResponse.Success -> {
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }
    private fun postValueEvent(value: Int, type: String) {
        val msgArrayList = arrayOf(
            "Api 오류 : $type 실패했습니다.",
            "서버 오류 : $type 실패했습니다.",
            "알 수 없는 오류 : $type 실패했습니다."
        )

        when (value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }
}