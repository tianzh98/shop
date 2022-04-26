package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long brandId;

    /**
     * 
     */
    private Long productCategoryId;

    /**
     * 
     */
    private Long productAttributeCategoryId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String pic;

    /**
     * 货号
     */
    private String productSn;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;

    /**
     * 上架状态：0->下架；1->上架
     */
    private Integer publishStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 销量
     */
    private Integer sale;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 市场价
     */
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 库存预警值
     */
    private Integer lowStock;

    /**
     * 单位
     */
    private String unit;

    /**
     * 商品重量，默认为克
     */
    private BigDecimal weight;

    /**
     * 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
     */
    private String serviceIds;

    /**
     * 
     */
    private String keywords;

    /**
     * 
     */
    private String note;

    /**
     * 图片id，连产品图片限制为5张，以逗号分割
     */
    private String picIdListStr;

    /**
     * 
     */
    private String detailTitle;

    /**
     * 
     */
    private String detailDesc;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品分类名称
     */
    private String productCategoryName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}