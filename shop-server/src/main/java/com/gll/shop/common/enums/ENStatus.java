package com.gll.shop.common.enums;

import lombok.Getter;

@Getter
public enum ENStatus {
    //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
    WAITEPAY("0","待付款"),
    NOTDELIVERY("1","待发货"),
    DELIVERY("2","已发货"),
    FINISHED("3","已完成"),
    CLOSED("4","已关闭"),
    INVALIDATEOrder("5","无效订单");


    public final String value;
    public final String label;

    ENStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }
    public static String getLabelByValue(String value) {
        for (ENStatus type : ENStatus.values()) {
            if (value.equals(type.getValue())) {
                return type.getLabel();
            }
        }
        return null;
    }
}
