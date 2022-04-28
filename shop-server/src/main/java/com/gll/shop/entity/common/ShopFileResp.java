package com.gll.shop.entity.common;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Assert;
import com.gll.shop.entity.ShopFile;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author gaoll
 * @date 2022年04月21日
 */
@Data
public class ShopFileResp implements Serializable {
    private static final long serialVersionUID = 7710626612182003568L;

    private String base64String;

    private Long id;

    private String fileType;


}
