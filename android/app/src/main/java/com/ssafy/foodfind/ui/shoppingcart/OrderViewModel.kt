package com.ssafy.foodfind.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.order.OrderRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _orderId = MutableLiveData<Int>()
    val orderId: LiveData<Int> = _orderId



    fun insertOrder(order: Order) {
        showProgress()
        viewModelScope.launch {
            val response = repository.insertOrderRequest(order)
            val type = "주문을"
            when(response) {
                is NetworkResponse.Success -> {
                    _orderId.postValue(response.body)
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