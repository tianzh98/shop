package com.gll.shop.controller.common;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ShopFile;
import com.gll.shop.entity.common.ShopFileResp;
import com.gll.shop.service.shopFile.ShopFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

/**
 * @author gaoll
 * @date 2022年04月10日
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileUploadController {

    private final ShopFileService shopFileService;

    public FileUploadController(ShopFileService shopFileService,
                                RedisTemplate redisTemplate) {
        this.shopFileService = shopFileService;
    }

    @PostMapping("/upload")
    public ResultContext<ShopFileResp> upload(@RequestBody MultipartFile file) throws IOException {


        byte[] fileBase64 = Base64.getEncoder().encode(file.getBytes());
        ShopFile shopFile = new ShopFile();
        shopFile.setFileType(file.getContentType());
        shopFile.setFileName(file.getOriginalFilename());
        shopFile.setFileAttr(file.getName());
        shopFile.setRowVersion(1);
        shopFile.setFileBase64(fileBase64);
        // 插入成功后   id值 会被赋予这个对象
        shopFileService.save(shopFile);

        ShopFileResp resp = new ShopFileResp();
        String base64String = new String(shopFile.getFileBase64());
        resp.setBase64String(base64String);
        resp.setId(shopFile.getId());
        resp.setFileType(shopFile.getFileType());

        return ResultContext.buildSuccess(null, resp);
    }

    @PostMapping("/getFileById")
    public ResultContext<ShopFileResp> getFileById(@RequestBody JSONObject param) throws IOException {
        Long id = param.getLong("id");
        ShopFileResp shopFileResp = shopFileService.getFileById(id);

        return ResultContext.buildSuccess(null, shopFileResp);
    }


}
