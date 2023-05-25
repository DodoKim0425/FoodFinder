package com.ssafy.foodfind.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.Order
import com.ssafy.foodfind.data.entity.OrderItem
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.order.OrderRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _orderId = MutableLiveData<Int>()
    val orderId: LiveData<Int> = _orderId

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _orderPhoneInfo = MutableLiveData<Order>()
    val orderPhoneInfo: LiveData<Order> = _orderPhoneInfo

    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems

    private val _isCancel = MutableLiveData<Event<Boolean>>()
    val isCancel: LiveData<Event<Boolean>> = _isCancel

    private val _isChanged = MutableLiveData<Event<Boolean>>()
    val isChanged: LiveData<Event<Boolean>> = _isChanged


    fun insertOrder(order: Order) {
        showProgress()
        viewModelScope.launch {
            val response = repository.insertOrderRequest(order)
            val type = "주문을"
            when (response) {
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


    fun updateOrder(order: Order) {
        showProgress()
        viewModelScope.launch {
            val response = repository.updateOrderStatus(order)
            val type = "주문을"
            when (response) {
                is NetworkResponse.Success -> {
                    _isChanged.postValue(Event(response.body))
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


    fun selectOrder(orderId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.selectOrder(orderId)
            val type = "주문을"
            when (response) {
                is NetworkResponse.Success -> {
                    _order.postValue(response.body)
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


    fun selectPhoneInfo(orderId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.selectOrder(orderId)
            val type = "주문을"
            when (response) {
                is NetworkResponse.Success -> {
                    _orderPhoneInfo.postValue(response.body)
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


    fun getRecentOrders(userId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.selectOrderByUserIdRequest(userId)
            val type = "주문 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _orders.postValue(response.body)
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


    fun getRecentOrdersByTruckId(truckId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.selectOrderByTruckId(truckId)
            val type = "주문 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _orders.postValue(response.body)
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


    fun getDetailOrder(orderId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.selectOrderItemDetailByOrderId(orderId)
            val type = "주문 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _orderItems.postValue(response.body)
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


    fun updateOrderToCancel(orderId: Int) {
        showProgress()
        viewModelScope.launch {
            val response = repository.updateOrderToCancel(orderId)
            val type = "주문 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _isCancel.postValue(Event(response.body))
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