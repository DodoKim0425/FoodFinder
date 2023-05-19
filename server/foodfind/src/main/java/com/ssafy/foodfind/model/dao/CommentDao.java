package com.ssafy.foodfind.model.dao;

import java.util.List;

import com.ssafy.foodfind.model.dto.Comment;

public interface CommentDao {
	 List<Comment> selectCommentByTruck(String truckId);
	 List<Comment> selectCommentByUser(String userId);
	 int insert(Comment comment);
	 int update(Comment comment);
}
