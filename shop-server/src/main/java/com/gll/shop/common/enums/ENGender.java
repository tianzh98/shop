package com.gll.shop.common.enums;

import lombok.Getter;

@Getter
public enum ENGender {
    //性别（1:女、 2:男）
    MAN("2","男"),
    WOMAN("1","女");

    public final String value;
    public final String label;

    ENGender(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
