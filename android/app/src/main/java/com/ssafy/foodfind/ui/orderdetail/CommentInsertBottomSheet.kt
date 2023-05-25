package com.ssafy.foodfind.ui.orderdetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.Comment
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.databinding.BottomSheetCommentInsertBinding

class CommentInsertBottomSheet(context: Context): BottomSheetDialog(context) {
	private lateinit var binding : BottomSheetCommentInsertBinding
	var listener : OnSendFromBottomSheetDialog?=null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_comment_insert, null, false)

		setContentView(binding.root)

		binding.commentInsertBtn.setOnClickListener {
			var rating = binding.commentInsertRating.rating
			var content = binding.etInsertCommentContent.text.toString()

			if(content !="" ){
				listener?.sendValue(
					Comment(0, 0, 0, rating, content, "")
				)
				dismiss()
			}else{
				Toast.makeText(context, "입력창을 채워주세요", Toast.LENGTH_SHORT).show()
			}

		}

	}

	interface  OnSendFromBottomSheetDialog{
		fun sendValue(value : Comment)
	}
}