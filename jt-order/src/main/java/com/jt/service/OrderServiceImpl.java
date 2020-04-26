package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements DubboOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	//@Resource(name="order_jt")
	@Autowired
	private AmqpTemplate at;
	//@Resource(name="ship_jt")
	//private AmqpTemplate atship;
	//@Resource(name="item_jt")
	//private AmqpTemplate atitem;
	/**
	 * 通过order封装3个对象
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public String saveOrder(Order order) {
		//1.动态生成id
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		//2分别入库
		Date date=new Date();
		order.setOrderId(orderId)
		.setStatus(1)
		.setCreated(date)
		.setUpdated(date);
		at.convertAndSend("order_jt",order);
		//orderMapper.insert(order);
		//3.物流入库
		OrderShipping ship = order.getOrderShipping();
		ship.setOrderId(orderId)
		.setCreated(date)
		.setUpdated(date);
		at.convertAndSend("ship_jt",ship);
		//orderShippingMapper.insert(ship);
		//4.订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId)
			         .setCreated(date)
			         .setUpdated(date);
			at.convertAndSend("item_jt",orderItem);
			//orderItemMapper.insert(orderItem);
		}
		System.out.println("入库成功");
		return orderId;
	}
	@Override
	public Order findOrderById(String orderId) {
		Order order = orderMapper.selectById(orderId);
		OrderShipping orderShip = orderShippingMapper.selectById(orderId);
		QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("order_id", orderId);
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		order.setOrderItems(orderItems).setOrderShipping(orderShip);
		return order;
	}
}
