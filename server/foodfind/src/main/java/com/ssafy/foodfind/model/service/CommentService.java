package com.ssafy.foodfind.model.service;

import java.util.List;

import com.ssafy.foodfind.model.dto.Comment;

public interface CommentService {
	public List<Comment> selectCommentByTruck(String truckId);
	public List<Comment> selectCommentByUser(String userId);
	public Boolean insert(Comment comment);
	public Boolean update(Comment comment);
	public void delete(String commentId);
}
