package com.ssafy.foodfind.model.dao;

import com.ssafy.foodfind.model.dto.Comment;

public interface CommentDao {
	 Comment selectCommentByTruck(String truckId);
}
