package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface DubboCatService {

	List<Cart> findCardById(Long userId);

	void updateNum(Cart cart);

	void deleteItemById(Cart cart);

	void insert(Cart cart);

}
