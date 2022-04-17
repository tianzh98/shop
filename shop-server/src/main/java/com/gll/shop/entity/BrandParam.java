package com.gll.shop.entity;

import com.gll.shop.entity.common.BaseQueryParams;
import lombok.Data;

@Data
public class BrandParam extends BaseQueryParams {
    private static final long serialVersionUID = 1734121161222672412L;
    //商品的品牌id
    public Long id;
}
