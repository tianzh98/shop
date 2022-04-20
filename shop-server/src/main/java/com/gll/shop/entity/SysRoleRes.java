package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色和资源关联表
 * @TableName sys_role_res
 */
@TableName(value ="sys_role_res")
@Data
public class SysRoleRes implements Serializable {
    /**
     * 角色id
     */
    @TableId
    private String roleId;

    /**
     * 资源id
     */
    private String resId;

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