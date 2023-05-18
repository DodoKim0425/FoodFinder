package com.ssafy.foodfind.model.service;

import com.ssafy.foodfind.model.dto.Comment;

public interface CommentService {
	public Comment selectCommentByTruck(String userId);
}
