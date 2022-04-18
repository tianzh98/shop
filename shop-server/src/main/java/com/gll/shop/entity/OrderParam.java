package com.gll.shop.entity;

import com.gll.shop.entity.common.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderParam  extends BaseQueryParams {
    private static final long serialVersionUID = -1281577577956252499L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 用户帐号
     */
    private String memberUsername;
    /**
     * 支付方式：0->未支付；1->支付宝；2->微信
     */
    private Integer payType;
    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     */
    private Integer status;
    
}
