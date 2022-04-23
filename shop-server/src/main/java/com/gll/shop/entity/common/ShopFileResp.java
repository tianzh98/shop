package com.gll.shop.entity.common;

import cn.hutool.core.lang.Assert;
import com.gll.shop.entity.ShopFile;
import lombok.Data;

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

    public static ShopFileResp convert(ShopFile shopFile) {
        Assert.notNull(shopFile);
        String base64 = new String(shopFile.getFileBase64());

        ShopFileResp resp = new ShopFileResp();
        resp.setBase64String(base64);
        resp.setId(shopFile.getId());
        resp.setFileType(shopFile.getFileType());
        return resp;
    }
}
