package com.gll.shop.common.enums;

import lombok.Getter;

@Getter
public enum ENUserRole {
    //性别（1:女、 2:男）
    ADMIN("1","管理员"),
    NORMAL("2","普通用户");

    public final String value;
    public final String label;

    ENUserRole(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
