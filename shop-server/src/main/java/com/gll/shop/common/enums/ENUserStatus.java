package com.gll.shop.common.enums;

import java.util.stream.Stream;

/**
 * @author gaoll
 * @date 2022年03月27日
 */
public enum ENUserStatus {
    //用户 帐号状态（0、正常；1、禁用）
    NORMAL("0", "正常"),
    DISABLE("1", "禁用");

    public final String value;
    public final String label;

    ENUserStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据code取枚举对象
     *
     * @param value
     * @return
     */
    public static ENUserStatus getByValue(String value) {
        return Stream.of(ENUserStatus.values())
                .filter(userInfoStatusEnum -> userInfoStatusEnum.value.equals(value))
                .findAny()
                .orElse(null);
    }
}
