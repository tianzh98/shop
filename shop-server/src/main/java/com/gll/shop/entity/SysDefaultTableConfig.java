package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sys_default_table_config
 */
@TableName(value ="sys_default_table_config")
@Data
public class SysDefaultTableConfig implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 表名称
     */
    private String tablePageName;

    /**
     * PO属性名称
     */
    private String fieldName;

    /**
     * field_name对应前端要展示的列名称
     */
    private String showName;

    /**
     * 是否展示
     */
    private String isShow;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 排序字段（数据库表字段名）
     */
    private String orderBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}