package com.ssafy.foodfind.data.repository.comment

import com.ssafy.foodfind.data.entity.Comment
import com.ssafy.foodfind.data.entity.ErrorResponse
import com.ssafy.foodfind.data.network.NetworkResponse

interface CommentRepository {
	suspend fun getCommentByTruckResponse(truckId : Int): NetworkResponse<List<Comment>, ErrorResponse>
	suspend fun deleteCommentResponse(commentId : Int):NetworkResponse<Boolean, ErrorResponse>
	suspend fun updateCommentResponse(comment : Comment):NetworkResponse<Boolean, ErrorResponse>
}