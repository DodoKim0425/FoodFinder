package com.ssafy.foodfind.model.dao;

import java.util.List;

import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

public interface OrderDao {
	int insert(Order order);
	List<UserOrderItemDetail> selectOrderByOrderId(String orderId);
}
