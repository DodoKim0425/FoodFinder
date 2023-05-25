package com.ssafy.foodfind.data.repository.comment

import com.ssafy.foodfind.data.entity.Comment
import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.network.ApiService
import com.ssafy.foodfind.data.network.NetworkResponse
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
	private val apiService: ApiService
): CommentRepository{
	override suspend fun getCommentByTruckResponse(truckId: Int): NetworkResponse<List<Comment>, ErrorResponse> {
		return apiService.getCommentByTruckRequest(truckId)
	}

	override suspend fun deleteCommentResponse(commentId: Int): NetworkResponse<Boolean, ErrorResponse> {
		return apiService.deleteCommentRequest(commentId)
	}

	override suspend fun updateCommentResponse(comment: Comment): NetworkResponse<Boolean, ErrorResponse> {
		return apiService.updateCommentRequest(comment)
	}

	override suspend fun insertCommentResponse(comment: Comment): NetworkResponse<Boolean, ErrorResponse> {
		return apiService.insertCommentRequest(comment)
	}
}