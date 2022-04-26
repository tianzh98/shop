package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌表
 * @TableName brand
 */
@TableName(value ="brand")
@Data
public class Brand implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     * 
     */
    private Integer sort;

    /**
     * 
     */
    private Integer showStatus;

    /**
     * 品牌故事
     */
    private String brandStory;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}