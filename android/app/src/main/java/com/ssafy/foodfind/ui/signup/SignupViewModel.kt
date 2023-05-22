package com.ssafy.foodfind.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.foodfind.data.entity.Event
import com.ssafy.foodfind.data.entity.User
import com.ssafy.foodfind.data.network.NetworkResponse
import com.ssafy.foodfind.data.repository.user.UserRepository
import com.ssafy.foodfind.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg : LiveData<Event<String>> = _msg

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess : LiveData<Event<Boolean>> = _isSuccess

    private val _isDuplicate = MutableLiveData<Event<Boolean>>()
    val isDuplicate : LiveData<Event<Boolean>> = _isDuplicate


    fun signup(phoneNumber: String, password: String, passwordCheck: String, username: String) {
        if(validation(phoneNumber, password, passwordCheck, username)) {
            val user = User(phoneNumber = phoneNumber, password = password, username = username)

            showProgress()
            viewModelScope.launch {
                val response = repository.insertUser(user)
                val type = "회원가입에"
                when(response) {
                    is NetworkResponse.Success -> {
                        _isSuccess.postValue(Event(response.body))
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


    fun checkPhoneNumber(phoneNumber: String) {
        if(phoneNumber.isNotBlank()) {

            showProgress()
            viewModelScope.launch {
                val response = repository.checkPhoneNumber(phoneNumber)
                val type = "번호 확인에"
                when(response) {
                    is NetworkResponse.Success -> {
                        _isDuplicate.postValue(Event(response.body))
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


    private fun validation(phoneNumber : String, password: String, passwordCheck: String, username: String): Boolean {
        if (phoneNumber.isEmpty()) {
            _msg.postValue(Event("전화번호를 입력해주세요!"))
            return false
        } else if (password.isEmpty()) {
            _msg.postValue(Event("비밀번호를 입력해주세요!"))
            return false
        } else if (username.isEmpty()) {
            _msg.postValue(Event("사용자 별명을 입력해주세요!"))
            return false
        } else if(isDuplicate.value?.getContentIfNotHandled() == true) {
            _msg.postValue(Event("전화번호 중복확인을 해주세요!"))
            return false
        } else if(password != passwordCheck) {
            _msg.postValue(Event("비빌번호 확인과 값이 동일하지 않습니다!"))
            return false
        }
        return true
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