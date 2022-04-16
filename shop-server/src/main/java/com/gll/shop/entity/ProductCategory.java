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
     * 
     */
    private String name;

    /**
     * 分类级别：1->1级；2->2级
     */
    private Integer level;

    /**
     * 
     */
    private Integer productCount;

    /**
     * 
     */
    private String productUnit;

    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
    private Integer navStatus;

    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;

    /**
     * 
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 
     */
    private String keywords;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}