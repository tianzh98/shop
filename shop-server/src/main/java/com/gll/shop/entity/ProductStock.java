package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * product的库存
 * @TableName product_stock
 */
@TableName(value ="product_stock")
@Data
public class ProductStock implements Serializable {
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
     * product编码
     */
    private String productCode;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 预警库存
     */
    private Integer lowStock;

    /**
     * 销量
     */
    private Integer sale;

    /**
     * 商品销售属性，json格式
     */
    private String spData;

    /**
     * 版本号
     */
    private Integer rowVersion;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}