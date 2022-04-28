package com.gll.shop.controller.product;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.CartItem;
import com.gll.shop.entity.CartItemDTO;
import com.gll.shop.entity.CartItemParam;
import com.gll.shop.service.cartItem.CartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
//下面的每一个方法都会检查是否登录
@SaCheckLogin
public class CartItemController {
    public final  CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/getCartItemList")
    public ResultContext<IPage<CartItemDTO>> getCartItemList(@RequestBody CartItemParam param)
    {
        return cartItemService.getCartItemList(param);
    }
    @PostMapping("/addToCart")
    public ResultContext<String> addToCart(@RequestBody CartItem cartItem) {
        return cartItemService.addToCart(cartItem);
    }
    @PostMapping("/deleteCartById")
    public ResultContext<Void> deleteCartItem(@RequestBody  List<Long> ids)
    {
        return cartItemService.deleteCartItemById(ids);
    }
    @PostMapping("/updateCartItem")
    public ResultContext<Void> updateCartItem(@RequestBody CartItem cartItem)
    {
        return cartItemService.updateCartItem(cartItem);
    }
    @PostMapping("/getCartItemById")
    public ResultContext<CartItem> getCartItemById(@RequestBody  JSONObject jsonObject)
    {
        Long id = jsonObject.getLong("id");
        return cartItemService.getCartItemById(id);
    }
}
