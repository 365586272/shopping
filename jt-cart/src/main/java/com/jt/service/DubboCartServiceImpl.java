package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service
public class DubboCartServiceImpl implements DubboCatService{

	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCardById(Long userId) {
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	public void updateNum(Cart cart) {
		Cart cartTemp=new Cart();
		cartTemp.setNum(cart.getNum())
		.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper=new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
		.eq("item_id", cart.getItemId());
		cartMapper.update(cartTemp, updateWrapper);

	}

	@Override
	public void deleteItemById(Cart cart) {
		QueryWrapper<Cart> wrapper=new QueryWrapper<>(cart);
		cartMapper.delete(wrapper);		
	}
	/**
	 *数据库中是否存在该数据,不存在入库,存在更新数量 
	 */
	@Override
	public void insert(Cart cart) {
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId())
		             .eq("item_id", cart.getItemId());
		Cart cartDB=cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			cart.setCreated(new Date())
		    .setUpdated(cart.getCreated());
		cartMapper.insert(cart);
		}else {
			int num=cart.getNum()+cartDB.getNum();
			Cart cartTemp=new Cart();
			cartTemp.setId(cartDB.getId()).setNum(num).setUpdated(new Date());
			cartMapper.updateById(cartTemp);
		}
        
	}
}
