package com.ssafy.foodfind.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import com.ssafy.foodfind.R
import com.ssafy.foodfind.SharedPrefs
import com.ssafy.foodfind.databinding.ActivityLoginBinding
import com.ssafy.foodfind.databinding.ActivitySignupBinding
import com.ssafy.foodfind.ui.LoadingDialog
import com.ssafy.foodfind.ui.base.BaseActivity
import com.ssafy.foodfind.ui.home.MainActivity
import com.ssafy.foodfind.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {

    private val viewModel by viewModels<SignupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        observeData()
    }

    private fun initButton() {
        binding.joinBtn.setOnClickListener {
            if (binding.agreeAll.isChecked) {
                viewModel.signup(
                    binding.editTextPhone.text.toString(),
                    binding.editTextPassword.text.toString(),
                    binding.editTextPasswordCheck.text.toString(),
                    binding.editTextNickname.text.toString()
                )
            } else {
                showToast("약관들에 모두 동의해주세요.")
            }
        }

        binding.btnPhoneNumberCheck.setOnClickListener {
            viewModel.checkPhoneNumber(binding.editTextPhone.text.toString())
        }

        val checkListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.agree_all -> {
                        binding.agree1.isChecked = true
                        binding.agree2.isChecked = true
                        binding.agree3.isChecked = true
                    }
                    R.id.agree1 -> if (binding.agree1.isChecked && binding.agree2.isChecked && binding.agree3.isChecked) binding.agreeAll.isChecked =
                        true
                    R.id.agree2 -> if (binding.agree1.isChecked && binding.agree2.isChecked && binding.agree3.isChecked) binding.agreeAll.isChecked =
                        true
                    R.id.agree3 -> if (binding.agree1.isChecked && binding.agree2.isChecked && binding.agree3.isChecked) binding.agreeAll.isChecked =
                        true
                }
            } else {
                when (buttonView.id) {
                    R.id.agree1 -> binding.agreeAll.isChecked = false
                    R.id.agree2 -> binding.agreeAll.isChecked = false
                    R.id.agree3 -> binding.agreeAll.isChecked = false
                }
            }
        }

        binding.agreeAll.setOnCheckedChangeListener(checkListener)
        binding.agree1.setOnCheckedChangeListener(checkListener)
        binding.agree2.setOnCheckedChangeListener(checkListener)
        binding.agree3.setOnCheckedChangeListener(checkListener)
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@SignupActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@SignupActivity)
            isLoading.observe(this@SignupActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            isDuplicate.observe(this@SignupActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        showToast("중복된 전화번호입니다.")
                    } else {
                        showToast("사용가능한 전화번호입니다.")
                    }
                }
            }

            isSuccess.observe(this@SignupActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        showToast("회원가입에 성공했습니다.")
                        finish()
                    } else {
                        showToast("회원가입에 실패했습니다.")
                    }
                }
            }
        }
    }
}