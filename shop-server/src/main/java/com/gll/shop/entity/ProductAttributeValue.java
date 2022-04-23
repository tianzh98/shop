package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 存储产品参数信息的表
 * @TableName product_attribute_value
 */
@TableName(value ="product_attribute_value")
@Data
public class ProductAttributeValue implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long productId;

    /**
     * 
     */
    private Long productAttributeId;

    /**
     * 手动添加规格可选值或参数的值，参数单值，规格有多个时以逗号隔开
     */
    private String value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}