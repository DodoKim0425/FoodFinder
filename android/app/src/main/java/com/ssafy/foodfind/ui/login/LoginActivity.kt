package com.ssafy.foodfind.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.databinding.ActivityLoginBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.home.MainActivity
import com.ssafy.foodfind.ui.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        observeData()
    }

    private fun initButton() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.editTextPhoneNumber.text.toString(), binding.editTextPassword.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@LoginActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@LoginActivity)
            isLoading.observe(this@LoginActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                }
                else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            user.observe(this@LoginActivity) {
                if(it.userId != -1) {
                    SharedPrefs.saveUserInfo(it)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}