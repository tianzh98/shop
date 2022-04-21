package com.gll.shop.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author gaoll
 * @date 2022年04月21日
 */
@Data
public class UploadFileResp implements Serializable {
    private static final long serialVersionUID = 7710626612182003568L;

    private String base64String;

    private Long id;

    private String fileType;
}
