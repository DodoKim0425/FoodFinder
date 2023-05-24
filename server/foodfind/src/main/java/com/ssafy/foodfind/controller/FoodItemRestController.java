package com.ssafy.foodfind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.FoodItem;
import com.ssafy.foodfind.model.service.FoodItemService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/foodItem")
public class FoodItemRestController {

	@Autowired
	FoodItemService fService;
	
	@PostMapping("/insert")
	@ApiOperation(value="FoodItem을 추가한다. 성공하면 true를 리턴한다.")
    public boolean join(@RequestBody FoodItem foodItem) {
    	return fService.insert(foodItem);
    }
	
	@PostMapping("/insertAll")
	@ApiOperation(value="한번에 여러개의 FoodItem을 추가한다")
	public void insertAll(@RequestBody List<FoodItem> foodItems) {
		fService.insertAll(foodItems);
	}
	
	@GetMapping("/selectById")
	@ApiOperation(value="itemId로 FoodItem하나를 조회함.")
	public FoodItem selectFoodItemById(String itemId) {
		return fService.selectFoodItemById(itemId);
	}
	
	@GetMapping("/selectByTruckId")
	@ApiOperation(value="TruckId로 트럭의 전체 FoodItem을 조회함. usingYN이 false인 foodItem은 제외하고 반환한다")
	public List<FoodItem> selectFoodItemByTruck(String truckId) {
		return fService.selectFoodItemByTruck(truckId);
	}
	
	@PutMapping("/updateItemToNotUse/{itemId}")
	@ApiOperation(value="itemId로 해당 foodItem을 사용하지 않도록 변경, foodItem삭제로 볼 수있음.")
	public boolean updateItemToNotUse(@PathVariable String itemId) {
		return fService.updateFoodItemToNotUse(itemId);
	}
	
	@PutMapping("/updateItem")
	@ApiOperation(value="itemId로 해당 foodItem을 수정, name, description, price, status만 변경가능")
	public boolean updateItem(@RequestBody FoodItem foodItem) {
		return fService.updateFoodItem(foodItem);
	}
	
}
