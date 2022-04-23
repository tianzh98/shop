package com.gll.shop.common.enums;

/**
 * @author gaoll
 * @date 2022年04月23日
 */
public enum ENProductAttributeInputType implements LabelAndValue<Integer> {

    ATTR(0, "手工录入"),

    PARAM(1, "从列表中选取");


    private final Integer value;
    private final String label;

    ENProductAttributeInputType(Integer value, String label) {
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
