package com.ssafy.foodfind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.Comment;
import com.ssafy.foodfind.model.dto.User;
import com.ssafy.foodfind.model.service.CommentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/comment")
public class CommentRestController {

	@Autowired
	CommentService cService;
	
	@GetMapping("/getCommentByTruck")
    @ApiOperation(value="truckId로 해당 트럭의 코멘트들을 조회", response = List.class)
	public List<Comment> getCommentByTruck(String truckId){
		return cService.selectCommentByTruck(truckId);
	}
	
	@GetMapping("/getCommentByUser")
    @ApiOperation(value="userId로 해당 유저의 코멘트들을 조회", response = List.class)
	public List<Comment> getCommentByUser(String userId){
		return cService.selectCommentByUser(userId);
	}
	
	@PostMapping("/insert")
    @ApiOperation(value="코멘트를 추가, 성공하면 true반환 실패하면 false반환", response = Boolean.class)
	public Boolean insert(@RequestBody Comment comment) {
		return cService.insert(comment);
	}
	
	@PostMapping("/update")
    @ApiOperation(value="코멘트를 수정, 성공하면 true반환 실패하면 false반환", response = Boolean.class)
	public boolean update(@RequestBody Comment comment) {
		return cService.update(comment);
	}
}
