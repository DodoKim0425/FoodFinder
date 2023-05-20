package com.ssafy.foodfind.model.service;

import java.util.List;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

public interface OrderService {
	public int makeOrder(Order order);
	public List<UserOrderItemDetail> selectOrderTotalInfo(String orderId);
}
