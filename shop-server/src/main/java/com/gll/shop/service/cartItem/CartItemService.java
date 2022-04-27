package com.gll.shop.service.cartItem;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.CartItem;
import com.gll.shop.entity.CartItemDTO;
import com.gll.shop.entity.CartItemParam;

import java.util.List;

/**
 *
 */
public interface CartItemService extends IService<CartItem> {

    ResultContext<String> addToCart(CartItem param);

    ResultContext<IPage<CartItemDTO>> getCartItemList(CartItemParam param);

    ResultContext<Void> deleteCartItemById(List<Long> ids);

    ResultContext<Void> updateCartItem(CartItem cartItem);

    ResultContext<CartItem> getCartItemById(Long id);
}
