package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品分类
 * @TableName product_category
 */
@TableName(value ="product_category")
@Data
public class ProductCategory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 上机分类的编号：0表示一级分类
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类级别：1->1级；2->2级
     */
    private Integer level;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 数量单位
     */
    private String productUnit;

    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}