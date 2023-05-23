package com.ssafy.foodfind.ui.managetruck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.truck.TruckRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruckViewModel @Inject constructor(
    private val repository: TruckRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _truck = MutableLiveData<Event<Truck>>()
    val truck: LiveData<Event<Truck>> = _truck


    fun getMyTruckInfo(ownerId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.getMyTruckInfoRequest(ownerId)
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