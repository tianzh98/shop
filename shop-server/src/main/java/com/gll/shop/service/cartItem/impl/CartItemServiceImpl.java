package com.gll.shop.service.cartItem.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.*;
import com.gll.shop.entity.common.ShopFileResp;
import com.gll.shop.mapper.CartItemMapper;
import com.gll.shop.mapper.ProductStockMapper;
import com.gll.shop.service.cartItem.CartItemService;
import com.gll.shop.service.shopFile.ShopFileService;
import com.gll.shop.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem>
        implements CartItemService {

    private final ProductStockMapper productStockMapper;
    private final SysUserService sysUserService;
    private final ShopFileService shopFileService;

    @Autowired
    public CartItemServiceImpl(ProductStockMapper productStockMapper,
                               SysUserService sysUserService,
                               ShopFileService shopFileService) {
        this.productStockMapper = productStockMapper;
        this.sysUserService = sysUserService;
        this.shopFileService = shopFileService;
    }

    @Override
    public ResultContext<String> addToCart(CartItem param) {
        // 检查购物车是否有相同商品 商品id和商品库存id
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

    @Override
    public ResultContext<IPage<CartItemDTO>> getCartItemList(CartItemParam param) {
        IPage<CartItem> page = new Page<>(param.getPageNum(), param.getPageSize());
        long userId = StpUtil.getLoginIdAsLong();
        //商品品牌名称，商品名称,只查询用户自己的购物车
        page = getBaseMapper().selectPage(page, Wrappers.<CartItem>lambdaQuery()
                .eq(CartItem::getUserId, userId)
                .like(StrUtil.isNotBlank(param.getProductBrand()), CartItem::getProductBrand, param.getProductBrand())
                .like(StrUtil.isNotBlank(param.getProductName()), CartItem::getProductName, param.getProductName())
        );
        return ResultContext.buildSuccess("成功得到购物车列表", page.convert(this::translation));
    }

    @Override
    public ResultContext<Void> deleteCartItemById(List<Long> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        if (result <= 0) {
            return ResultContext.businessFail("删除购物车失败");
        }
        return ResultContext.success("成功删除购物车");
    }

    @Override
    public ResultContext<Void> updateCartItem(CartItem cartItem) {
        int result = getBaseMapper().updateById(cartItem);
        if (result <= 0) {
            return ResultContext.businessFail("更新购物车失败");
        }
        return ResultContext.success("更新购物车成功");
    }

    @Override
    public ResultContext<CartItem> getCartItemById(Long id) {
        return ResultContext.buildSuccess("成功获取购物车", getBaseMapper().selectById(id));
    }

    public CartItemDTO translation(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        BeanUtil.copyProperties(cartItem, cartItemDTO);
        //根据user_id获取user的real_name
        SysUser user = sysUserService.getBaseMapper().selectById(cartItem.getUserId());
        cartItemDTO.setUserName(user.getRealName());
        //根据pictureid获取图片
        ShopFileResp fileById = shopFileService.getFileById(cartItem.getMainPicId());
        cartItemDTO.setMainPictureFile(fileById);
        return cartItemDTO;
    }
}




