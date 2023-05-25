package com.ssafy.foodfind.ui.truckinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.data.entity.Comment
import com.ssafy.foodfind.data.entity.FoodItem
import com.ssafy.foodfind.databinding.ItemCommentBinding
import com.ssafy.foodfind.databinding.ItemFoodBinding
import com.ssafy.foodfind.ui.managetruck.FoodTruckAdapter

class TruckCommentAdapter(private val commentList:MutableList<Comment>, private val userId : Int):
	RecyclerView.Adapter<TruckCommentAdapter.CommentViewHolder>(){

	inner class CommentViewHolder (private val binding: ItemCommentBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(comment: Comment) {
			binding.comment = comment
			binding.executePendingBindings()
			if(comment.userId==userId){
				binding.commentButtons.visibility = View.VISIBLE
			}else{
				binding.commentButtons.visibility = View.GONE
			}
			binding.commentDeleteBtn.setOnClickListener {
				clickListener.onDeleteClicked(comment)
			}
			binding.commentUpdateBtn.setOnClickListener {
				clickListener.onUpdateClicked(comment)
			}
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): TruckCommentAdapter.CommentViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemCommentBinding.inflate(inflater, parent, false)
		return CommentViewHolder(binding)
	}

	override fun onBindViewHolder(holder: TruckCommentAdapter.CommentViewHolder, position: Int) {
		val comment = commentList[position]
		holder.bind(comment)
	}

	override fun getItemCount(): Int {
		return commentList.size
	}

	interface ClickListener{
		fun onDeleteClicked(comment : Comment)
		fun onUpdateClicked(comment: Comment)
	}
	lateinit var clickListener : ClickListener
}