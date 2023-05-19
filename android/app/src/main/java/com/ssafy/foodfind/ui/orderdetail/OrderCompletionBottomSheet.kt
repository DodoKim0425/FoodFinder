package com.ssafy.foodfind.ui.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.foodfind.R
import com.ssafy.foodfind.databinding.BottomSheetOrderCompletionBinding
import dagger.hilt.android.AndroidEntryPoint

class OrderCompletionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetOrderCompletionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_order_completion,
            container,
            false
        )
        binding.lifecycleOwner = this

        binding.btnConfirm.setOnClickListener {
            val completionTime = binding.etCompletionTime.text.toString().toIntOrNull()
            if (completionTime != null && completionTime > 0) {
                // Process the completion time
                // You can use completionTime as desired (e.g., save to database, perform further actions)
                dismiss()
            } else {
                // Show an error message for invalid input
                // You can display an error message to the user indicating that the input is invalid
                // and prompt them to enter a valid completion time
            }
        }

        return binding.root
    }
}
