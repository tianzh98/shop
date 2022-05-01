package com.gll.shop.entity;

import com.gll.shop.entity.common.ShopFileResp;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoll
 * @date 2022年05月01日
 */
@Getter
@Setter
public class OrderItemDTO extends OrderItem {
    private ShopFileResp shopFileResp;
}
