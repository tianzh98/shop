package com.gll.shop.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeDTO extends  ProductAttributePO {
    private static final long serialVersionUID = -7206098585979701640L;
    /**
     * 属性选择类型：0->唯一；1->单选；2->多选
     */
    private String selectTypeName;

}
