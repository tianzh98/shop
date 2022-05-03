package com.gll.shop.common.enums;

public enum ENPayType {
    //支付方式：0->未支付；1->支付宝；2->微信
    ALIPAY("1","支付宝"),
    WEIXIN("2","微信"),
    NOTPAY("0","未支付");


    public final String value;
    public final String label;

    ENPayType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
    public static String getLabelByValue(String value) {
        for (ENPayType type : ENPayType.values()) {
            if (value.equals(type.getValue())) {
                return type.getLabel();
            }
        }
        return null;
    }

    public Integer getIntValue() {
        return Integer.parseInt(value);
    }
}
