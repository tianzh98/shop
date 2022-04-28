package com.gll.shop.entity;

import com.gll.shop.entity.common.BaseQueryParams;
import lombok.Data;

@Data
public class ReceiveAddressParam extends BaseQueryParams {
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货人具体的地址
     */
    private String detailAddress;
}
