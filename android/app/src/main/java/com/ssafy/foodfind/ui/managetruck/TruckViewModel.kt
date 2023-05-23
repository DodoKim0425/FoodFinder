package com.ssafy.foodfind.ui.managetruck

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.foodItem.FoodItemRepository
import com.ssafy.foodfind.data.repository.truck.TruckRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TruckViewModel_싸피"
@HiltViewModel
class TruckViewModel @Inject constructor(
    private val truckRepository: TruckRepository,
    private val foodItemRepository: FoodItemRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _truck = MutableLiveData<Event<Truck>>()
    val truck: LiveData<Event<Truck>> = _truck

    private val _foodItemList = MutableLiveData<Event<List<FoodItem>>>()
    val foodItemList : LiveData<Event<List<FoodItem>>> = _foodItemList

    private val _isMapReady = MutableLiveData<Boolean>()
    val isMapReady : LiveData<Boolean> = _isMapReady

    fun getMyTruckInfo(ownerId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.getMyTruckInfo(ownerId)
            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _truck.postValue(Event(response.body))
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

    fun getFoodItem(truckId : Int){
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
    fun updateTruck(truck:Truck){
        showProgress()
        viewModelScope.launch {
            val response = truckRepository.updateTruck(truck)
            val type = "업데이트에"
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

    fun setIsMapReady(){
        _isMapReady.postValue(true)
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