package com.gll.shop.entity;

import com.gll.shop.entity.common.ShopFileResp;
import lombok.Data;

@Data
public class CartItemDTO extends  CartItem{

    private static final long serialVersionUID = -2553023448988790659L;
    /**
     * 顾客姓名
     */
    private String userName;
    /**
     * 商品图片
     */
    private ShopFileResp mainPictureFile;
}
