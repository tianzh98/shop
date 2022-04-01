package com.gll.shop.common.enums;

import java.util.stream.Stream;

/**
 * @Author：dcy
 * @Description: 资源类型枚举类
 * @Date: 2021/8/24 11:04
 */
public enum ENResourcesType {

    //资源 类型（1、目录；2、菜单；3、按钮）
    CATALOGUE("1", "目录"),
    MENU("2", "菜单"),
    BUTTON("3", "按钮"),
    ;

    public final String value;
    public final String label;

    ENResourcesType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据code取枚举对象
     *
     * @param code
     * @return
     */
    public static ENResourcesType getByCode(String code) {
        return Stream.of(ENResourcesType.values())
                .filter(enResourcesType -> enResourcesType.value.equals(code))
                .findAny()
                .orElse(null);
    }
}
