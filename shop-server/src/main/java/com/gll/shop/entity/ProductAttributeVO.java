package com.gll.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 新增/编辑商品使用
 *
 * @author gaoll
 * @date 2022年04月20日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductAttributeVO extends ProductAttribute {
    private static final long serialVersionUID = -1258514554901185129L;

    /**
     * 规格可选项
     */
    private List<String> options;

    /**
     * 选中的规格值 type ==0
     */
    private List<String> values;


    /**
     * 参数具体值   type == 1
     */
    private String value;

}
