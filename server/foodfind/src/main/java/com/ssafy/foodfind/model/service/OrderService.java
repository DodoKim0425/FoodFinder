package com.ssafy.foodfind.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

public interface OrderService {
	public int makeOrder(Order order);
	public List<UserOrderItemDetail> selectOrderItemDetailByOrderId(String orderId);
	public List<Map<String, Object>> getOrderByUser(String orderId);
	public boolean updateOrderToCancel(String orderId);
	public Map<String, Object> selectOrder(String orderId);
	public boolean updateOrderStatus(Order order);
	public List<Order> selectOrderByTruck(String truckId);
}
