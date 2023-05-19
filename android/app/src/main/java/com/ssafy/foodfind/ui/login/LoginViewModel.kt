package com.ssafy.foodfind.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.login.LoginRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg : LiveData<Event<String>> = _msg

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user


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


    fun login(phoneNumber: String, password: String) {
        if(validation(phoneNumber, password)) {
            val user = HashMap<String, String>()
            user["phoneNumber"] = phoneNumber
            user["password"] = password

            showProgress()
            viewModelScope.launch {
                val response = repository.login(user)
                val type = "로그인에"
                when(response) {
                    is NetworkResponse.Success -> {
                        _user.postValue(response.body)
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
    }


    private fun validation(phoneNumber : String, password: String): Boolean {
        if (phoneNumber.isEmpty() || password.isEmpty()) {
            _msg.postValue(Event("아이디와 비밀번호를 입력해주세요!"))
            return false
        }
        return true
    }
}