package com.gll.shop.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;


/**
 *
 */
public enum ENYesOrNo implements LabelAndValue<String> {
    //
    YES("1", "是"),
    NO("0", "否"),
    ;


    private String value;
    private String label;

    ENYesOrNo(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public static String getLabelByValue(String value) {
        return StrUtil.isBlank(value) ? null :
                Arrays.stream(ENYesOrNo.values())
                        .filter(x -> x.value.equals(value)).findAny().orElseThrow(() -> new RuntimeException("出现未知枚举值")).label;
    }

    public static boolean isYes(String value) {
        return YES.value.equals(value);
    }

    public static boolean isNo(String value) {
        return NO.value.equals(value);
    }

    public static ENYesOrNo convertByLabel(String label) {
        for (ENYesOrNo st : ENYesOrNo.values()) {
            if (st.getLabel().equals(label)) {
                return st;
            }
        }
        return null;
    }

}
