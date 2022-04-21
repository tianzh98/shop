package com.gll.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 画册图片表
 * @TableName shop_file
 */
@TableName(value ="shop_file")
@Data
public class ShopFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件属性
     */
    private String fileAttr;

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

    /**
     * 图片
     */
    private byte[] fileBase64;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}