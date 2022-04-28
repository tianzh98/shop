package com.gll.shop.entity;

import com.gll.shop.entity.common.BaseQueryParams;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemParam extends BaseQueryParams implements Serializable {
    private static final long serialVersionUID = -7597460374226412074L;
    /**
     * 商品品牌
     */
    private  String  ProductBrand;
    /**
     * 商品名称
     */
    private String  ProductName;
}
