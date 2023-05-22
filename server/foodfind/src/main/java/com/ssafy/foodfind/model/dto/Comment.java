package com.ssafy.foodfind.model.dto;

public class Comment {
	private int commentId;
	private int userId;
	private int truckId;
	private int rating;
	private String content;
	private String username;
	
	public Comment() {
		super();
	}
	
	public Comment(int commentId, int userId, int truckId, int rating, String content, String username) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.truckId = truckId;
		this.rating = rating;
		this.content = content;
		this.username=username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTruckId() {
		return truckId;
	}
	public void setTruckId(int truckId) {
		this.truckId = truckId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userId=" + userId + ", truckId=" + truckId + ", rating=" + rating
				+ ", content=" + content + ", username=" + username + "]";
	}

}
