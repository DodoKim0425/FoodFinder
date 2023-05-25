package com.ssafy.foodfind.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

public interface OrderDao {
	int insert(Order order);
	List<UserOrderItemDetail> selectOrderItemDetailByOrderId(String orderId);
	List<Map<String, Object>> selectOrderByUser(String orderId);
	int updateOrderToCancel(String orderId);
	Map<String, Object> selectOrder(String orderId);
	int updateOrderStatus(Order order);
	List<Order>selectOrderByTruck(String truckId);
}
