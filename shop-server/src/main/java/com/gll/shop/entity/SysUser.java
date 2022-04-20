package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 性别（1:女、 2:男）
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 绑定的邮箱
     */
    private String email;

    /**
     * 绑定的手机号
     */
    private String phone;

    /**
     * 头像图片url路径
     */
    private String avatarPath;

    /**
     * 账号状态。0、正常；1、禁用
     */
    private String status;

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