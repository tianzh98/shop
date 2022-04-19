package com.gll.shop.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class ProductAttributePO implements Serializable {
    private static final long serialVersionUID = -7206098585979701640L;

    private Long id;

    /**
     *
     */
    private String name;

    /**
     * 属性选择类型：0->唯一；1->单选；2->多选
     */
    private Integer selectType;

    /**
     * 可选值列表，以逗号隔开
     */
    private String inputList;

    /**
     * 商品类别的名字
     */
    private String productTypeName;



}
