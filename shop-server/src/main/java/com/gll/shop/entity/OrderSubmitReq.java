package com.gll.shop.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提交订单的请求对象
 *
 * @author gaoll
 * @date 2022年05月03日
 */
@Data
public class OrderSubmitReq implements Serializable {

    private static final long serialVersionUID = 5339154687838666579L;


    private List<CartItem> cartItemList;

    private Long receiveAddressId;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 订单应付总金额（包括运费）
     */
    private BigDecimal priceSum;

}
