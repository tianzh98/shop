package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRole implements Serializable {
    /**
     * 角色id
     */
    @TableId
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0、正常；1、禁用）
     */
    private String roleStatus;

    /**
     * 数据范围（1、全部数据权限；2、自定数据权限；3、本部门数据权限；4、本部门及以下数据权限）
     */
    private String dataScope;

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