package com.gll.shop.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.CartItem;
import com.gll.shop.entity.ProductStock;
import com.gll.shop.mapper.ProductStockMapper;
import com.gll.shop.service.CartItemService;
import com.gll.shop.mapper.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem>
        implements CartItemService {

    private final ProductStockMapper productStockMapper;

    @Autowired
    public CartItemServiceImpl(ProductStockMapper productStockMapper) {
        this.productStockMapper = productStockMapper;
    }

    @Override
    public ResultContext<String> addToCart(CartItem param) {
        // 检查购物车是否有相同商品
        CartItem cartItem = getBaseMapper().selectOne(Wrappers.<CartItem>lambdaQuery()
                .eq(CartItem::getProductId, param.getProductId())
                .eq(CartItem::getProductStockId, param.getProductStockId()));

        // 如果有 则 更新数量，价格
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + param.getQuantity());
            cartItem.setPrice(param.getPrice());
        } else {
            cartItem = param;
            // 设置用户id
            cartItem.setUserId(StpUtil.getLoginIdAsLong());
        }

        // 校验库存
        ProductStock productStock = productStockMapper.selectById(cartItem.getProductStockId());
        if (productStock.getStock() < cartItem.getQuantity()) {
            return ResultContext.businessFail("商品库存不足！");
        }

        if (cartItem.getId() == null) {
            getBaseMapper().insert(cartItem);
        } else {
            getBaseMapper().updateById(cartItem);
        }

        return ResultContext.success("已加入购物车！");
    }
}




