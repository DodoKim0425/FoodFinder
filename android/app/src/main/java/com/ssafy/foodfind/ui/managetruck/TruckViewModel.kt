package com.ssafy.foodfind.ui.managetruck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.Food
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.truck.TruckRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruckViewModel @Inject constructor(
    private val repository: TruckRepository,
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _truck = MutableLiveData<Truck>()
    val truck: LiveData<Truck> = _truck

    private val _truckItem = MutableLiveData<List<Food>>()
    val truckItems: LiveData<List<Food>> = _truckItem

    fun getMyTruckInfo(ownerId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.getMyTruckInfoRequest(ownerId)
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
            val response = repository.getTruckRequest(truckId)
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
            val response = repository.getTruckItemRequest(truckId)
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