package com.ssafy.foodfind.ui.truckinfo

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
import com.ssafy.foodfind.databinding.BottomSheetCommentUpdateBinding
import com.ssafy.foodfind.ui.managetruck.UpdateTruckItemBottomSheet

class UpdateCommentBottomSheet (context: Context, private var comment: Comment) : BottomSheetDialog(context){
	private lateinit var binding : BottomSheetCommentUpdateBinding
	var listener: OnSendFromBottomSheetDialog? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_comment_update, null, false)


		setContentView(binding.root)

		binding.comment=comment

		binding.commentUpdateBtn.setOnClickListener {
			if(binding.etUpdatedCommentContent.text.toString()!=""){
				var newComment = Comment(
					commentId = comment.commentId,
					userId = comment.userId,
					truckId = comment.truckId,
					rating = binding.commentUpdateRating.rating,
					content = binding.etUpdatedCommentContent.text.toString(),
					username = comment.username
				)
				listener?.sendValue(newComment)
				dismiss()
			}else{
				Toast.makeText(context, "입력창을 채워주세요", Toast.LENGTH_SHORT).show()
			}

		}

	}

	interface OnSendFromBottomSheetDialog{
		fun sendValue(value : Comment)
	}
}