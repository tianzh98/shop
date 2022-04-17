package com.gll.shop.entity;

import com.gll.shop.entity.common.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoll
 */
@Getter
@Setter
public class ProductParam extends BaseQueryParams {
    private static final long serialVersionUID = 5141320606177789234L;

    private Long id;

    /**
     * 分类
     */
    private Long productCategoryId;


    /**
     * 名称
     */
    private String name;


    /**
     * 货号
     */
    private String productSn;

    /**
     * 品牌
     */
    private Long brandId;

}
