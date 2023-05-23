package com.ssafy.foodfind.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.entity.TruckLocation
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.truck.TruckRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MapViewModel_싸피"

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: TruckRepository,
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _locations = MutableLiveData<List<TruckLocation>>()
    val locations: LiveData<List<TruckLocation>> = _locations

    private val _truck = MutableLiveData<Truck>()
    val truck: LiveData<Truck> = _truck

    fun getAllTruckLocation() {
        Log.d(TAG, "getAllTruckLocation: start")
            showProgress()
            viewModelScope.launch {
                val response = repository.getAllTruckLocationRequest()
                val type = "가게 조회"
                when(response) {
                    is NetworkResponse.Success -> {
                        _locations.postValue(response.body)
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
        Log.d(TAG, "getAllTruckLocation: start")
        showProgress()
        viewModelScope.launch {
            val response = repository.getTruckRequest(truckId)
            val type = "가게 조회"
            when(response) {
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


    private fun postValueEvent(value : Int, type: String) {
        val msgArrayList = arrayOf("Api 오류 : $type 실패했습니다.",
            "서버 오류 : $type 실패했습니다.",
            "알 수 없는 오류 : $type 실패했습니다."
        )

        when(value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }
}