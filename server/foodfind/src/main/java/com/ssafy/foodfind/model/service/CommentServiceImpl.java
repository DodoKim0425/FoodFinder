package com.ssafy.foodfind.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.foodfind.model.dao.CommentDao;
import com.ssafy.foodfind.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	private static CommentServiceImpl instance = new CommentServiceImpl();
	private CommentServiceImpl() {}
	public static CommentServiceImpl getInstance() {
		return instance;
	}
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<Comment> selectCommentByTruck(String truckId) {
		return commentDao.selectCommentByTruck(truckId);
	}
	@Override
	public List<Comment> selectCommentByUser(String userId) {
		return commentDao.selectCommentByUser(userId);
	}
	@Override
	public Boolean insert(Comment comment) {
		int res=commentDao.insert(comment);
		try {
			if(res==1) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
		
	}
	@Override
	public Boolean update(Comment comment) {
		int res=commentDao.update(comment);
		try {
			if(res==1) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}

}
