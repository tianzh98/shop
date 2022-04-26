package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资源表
 * @TableName sys_resource
 */
@TableName(value ="sys_resource")
@Data
public class SysResource implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型（1、目录；2、菜单；3、按钮）
     */
    private String type;

    /**
     * 菜单code
     */
    private String menuCode;

    /**
     * 父级id
     */
    private Integer pid;

    /**
     * 排序
     */
    private BigDecimal serialNo;

    /**
     * 标题（目录名称、菜单名称、按钮名称）
     */
    private String title;

    /**
     * 路由地址（目录和菜单）
     */
    private String url;

    /**
     * 权限标识（菜单和按钮）
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 状态（0、正常；1、禁用）
     */
    private String resStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 版本号
     */
    private Integer rowVersion;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}