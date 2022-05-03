package com.gll.shop.common.enums;

import cn.hutool.core.img.ImgUtil;

/**
 * @author gaoll
 * @date 2022年04月28日
 */
public enum ENCacheKey implements LabelAndValue<String> {


    IMAGE_SCALE("IMAGE:if_scale", "是否对返回给前端的图片进行缩放处理", false),
    IMAGE_WIDTH("IMAGE:width", "返回给前端图片统一宽度", 350),
    IMAGE_HEIGHT("IMAGE:height", "返回给前端图片统一高度", 400),
    IMAGE_TYPE("IMAGE:type", "返回给前端图片统一类型", ImgUtil.IMAGE_TYPE_JPG),
    ShopFileResp("ShopFileRespMap", "ShopFileRespMap,key是图片表的id", null),
    ORDER("ORDER:", "订单缓存，用于订单过期功能.结构：ORDER:id", null);

    private final String value;
    private final String label;
    private final Object defaultValue;


    ENCacheKey(String value, String label, Object defaultValue) {
        this.value = value;
        this.label = label;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
