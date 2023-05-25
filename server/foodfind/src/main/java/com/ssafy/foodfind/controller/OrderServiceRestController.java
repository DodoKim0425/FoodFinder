package com.ssafy.foodfind.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping("/selectOrderItemDetailByOrderId")
    @ApiOperation(value = "order에 대한 상세정보(orderItem) 반환한다")
    public List<UserOrderItemDetail> selectOrderItemDetailByOrderId(String orderId){
    	return oService.selectOrderItemDetailByOrderId(orderId);
    }
    
    @GetMapping("/selectOrderByUserId")
    @ApiOperation(value = "사용자의 주문 목록(order)을 반환한다. 트럭 이름을 name으로 보낸다")
    public List<Map<String, Object>> selectOrderByUserId(String userId){
    	return oService.getOrderByUser(userId);
    }
    
    @PutMapping("/updateOrderToCancel")
    @ApiOperation(value = "orderId로 주문을 취소한다.")
    public boolean updateOrderToCancel(String orderId) {
    	String orderStatus = (String) oService.selectOrder(orderId).get("orderStatus");
    	
    	if(orderStatus.equals("RECEIVED")) {
    		return oService.updateOrderToCancel(orderId);
    	}else {
    		return false;
    	}
    	
    }
    
    @GetMapping("/selectOrder")
    @ApiOperation(value = "orderId로 Order를 반환한다. 트럭 이름은 name으로 보낸다")
    public Map<String, Object> selectOrder(String orderId) {
    	return oService.selectOrder(orderId);
    }
    
    @PutMapping("/updateOrderStatus")
    @ApiOperation(value = "order상태를 변경")
    public boolean updateOrderStatus(@RequestBody Order order) {
    	return oService.updateOrderStatus(order);
    }
    
    @GetMapping("/selectOrderByTruckId")
    @ApiOperation(value = "truckId로 주문 목록을 조회한다, orderStatus로 먼저 정렬되고 orderStatus가 동일하면 orderTime이 오래된것 순으로 정렬")
    public List<Order> selectOrderByTruckId(String truckId){
    	return oService.selectOrderByTruck(truckId);
    }
    
}
