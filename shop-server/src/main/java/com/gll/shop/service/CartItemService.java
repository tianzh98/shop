package com.gll.shop.service;

import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface CartItemService extends IService<CartItem> {

    ResultContext<String> addToCart(CartItem param);

}
