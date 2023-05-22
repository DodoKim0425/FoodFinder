package com.ssafy.foodfind.model.service;

import java.util.List;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

public interface OrderService {
	public int makeOrder(Order order);
	public List<UserOrderItemDetail> selectOrderItemDetailByOrderId(String orderId);
	public List<Order> getOrderByUser(String orderId);
	public boolean updateOrderToCancel(String orderId);
	public Order selectOrder(String orderId);
	public boolean updateOrderStatus(Order order);
}
