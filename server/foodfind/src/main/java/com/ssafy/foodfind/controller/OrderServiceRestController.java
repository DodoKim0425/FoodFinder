package com.ssafy.foodfind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;
import com.ssafy.foodfind.model.service.OrderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/order")
public class OrderServiceRestController {
	
	@Autowired
	OrderService oService;
	
    @PostMapping("/insert")
    @ApiOperation(value = "order 객체를 저장하고 추가된 Order의 id를 반환한다.")
    public int makeOrder(@RequestBody Order order) {
        return oService.makeOrder(order);
    }
    
    @GetMapping("/selectOrderTotalInfo")
    @ApiOperation(value = "order에 대한 상세정보 반환한다")
    public List<UserOrderItemDetail> selectOrderTotalInfo(String orderId){
    	return oService.selectOrderTotalInfo(orderId);
    }
}
