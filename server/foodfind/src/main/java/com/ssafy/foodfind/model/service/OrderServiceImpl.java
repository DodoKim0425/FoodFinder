package com.ssafy.foodfind.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.foodfind.model.dao.OrderDao;
import com.ssafy.foodfind.model.dao.OrderItemDao;
import com.ssafy.foodfind.model.dto.Order;
import com.ssafy.foodfind.model.dto.UserOrderItemDetail;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Override
	@Transactional
	public int makeOrder(Order order) {
		orderDao.insert(order);
		
		for(int i=0;i<order.getItems().size();i++) {
			order.getItems().get(i).setOrderId(order.getOrderId());
			orderItemDao.insert(order.getItems().get(i));
		}
		
		return order.getOrderId();
	}

	@Override
	public List<UserOrderItemDetail> selectOrderTotalInfo(String orderId) {
		return orderDao.selectOrderByOrderId(orderId);
	}
	
}
