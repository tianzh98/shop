package com.gll.shop.common.enums;

public enum ENSelectType {
    //属性选择类型：0->唯一；1->单选；2->多选
    ONLY("0","唯一"),
    RADIO("1","单选"),
    MULTIPLE("2","多选");
    public final String value;
    public final String label;

    ENSelectType(String value, String label) {
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
        for (ENSelectType type : ENSelectType.values()) {
            if (value.equals(type.getValue())) {
                return type.getLabel();
            }
        }
        return null;
    }

}
