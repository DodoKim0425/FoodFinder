package com.ssafy.foodfind.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.Truck;
import com.ssafy.foodfind.model.dto.User;
import com.ssafy.foodfind.model.service.TruckService;
import com.ssafy.foodfind.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/truck")
public class TruckRestController {
	
	@Autowired
	TruckService tService;
	
	@Autowired
	UserService uService;
	
	@GetMapping("/myTruckInfo")
    @ApiOperation(value="ownerId로 해당 트럭의 정보를 조회", response = Truck.class)
	public Truck myTruckInfo(String ownerId) {
		return tService.selectMyTruckInfo(ownerId);
	}
	
	@GetMapping("/getUserTruckCount")
    @ApiOperation(value="ownerId로 해당 유저가 보유중인 트럭 수 조회", response = Integer.class)
	public int getUserTruckCount(String ownerId) {
		return tService.selectTruckCountByUser(ownerId);
	}
	
	@PostMapping("/insert")
	@Transactional
	@ApiOperation(value="Truck 추가", response = Boolean.class)
	public Boolean insert(@RequestBody Truck truck) {
		String ownerId=Integer.toString(truck.getOwnerId());
		int truckCount=tService.selectTruckCountByUser(ownerId);
		if(truckCount==0) {
			boolean res=tService.insert(truck);
			return res;
		}else {
			return false;
		}
		
	}
	
	@PutMapping("/update")
	@ApiOperation(value="Truck 수정")
	public Boolean update(@RequestBody Truck truck) {
		tService.update(truck);
		return true;
	}
	
	@GetMapping("/selectAllTruck")
	@ApiOperation(value="Truck 전체 조회")
	public List<Truck> selectAllTruck(){
		return tService.selectAllTruck();
	}
	
	@GetMapping("/selectTruckByTruckId")
	@ApiOperation(value="truckId로 트럭 조회")
	public Truck selectTruckByTruckId(String truckId){
		return tService.selectTruckByTruckId(truckId);
	}
	
	@GetMapping("/selectTruckLocations")
	@ApiOperation(value="truckId와 location목록을 조회")
	public List<Map<String, Object>>selectTrucklocations(){
		return tService.selectTrucklocations();
	}
}
