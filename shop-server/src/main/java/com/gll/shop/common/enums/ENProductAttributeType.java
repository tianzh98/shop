package com.gll.shop.common.enums;

/**
 * @author gaoll
 * @date 2022年04月23日
 */
public enum ENProductAttributeType implements LabelAndValue<Integer> {

    ATTR(0, "规格"),

    PARAM(1, "参数");


    private final Integer value;
    private final String label;

    ENProductAttributeType(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
