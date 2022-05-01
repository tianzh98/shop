package com.gll.shop.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gaoll
 * @date 2022年05月01日
 */
@Getter
@Setter
public class OrderDetail extends OrderDTO {

    /**
     * 订单是商品信息
     */
    private List<OrderItemDTO> orderItemList;
}
