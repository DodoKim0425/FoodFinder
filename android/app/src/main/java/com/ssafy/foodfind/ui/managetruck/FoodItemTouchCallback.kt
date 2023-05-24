package com.ssafy.foodfind.ui.managetruck

import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.foodfind.R

class FoodItemTouchCallback (val activity:ManageTruckActivity, val recyclerView: RecyclerView) : ItemTouchHelper.SimpleCallback(
	ItemTouchHelper.UP or ItemTouchHelper.DOWN,//위아래 스와이프
	ItemTouchHelper.LEFT
){

	override fun isLongPressDragEnabled(): Boolean {
		return false
	}
	override fun onMove(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		target: RecyclerView.ViewHolder
	): Boolean {

		return true
	}

	//아이템이 왼쪽 스와이프 될때
	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		(recyclerView.adapter as ManageTruckActivity.FoodItemUpdateAdapter).removeItem(viewHolder.layoutPosition)
	}

	override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
		super.onSelectedChanged(viewHolder, actionState)
		when(actionState){
			ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.ACTION_STATE_SWIPE->{
				(viewHolder as ManageTruckActivity.FoodItemUpdateAdapter.CustomViewHolder).setBackgound(ResourcesCompat.getColor(recyclerView.context.resources, R.color.main_color5, null))
			}
		}
	}

	override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
		super.clearView(recyclerView, viewHolder)
		(viewHolder as ManageTruckActivity.FoodItemUpdateAdapter.CustomViewHolder).setBackgound(Color.WHITE)
	}
}