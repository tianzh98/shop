package com.gll.shop.controller.common;

import cn.hutool.json.JSONObject;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ShopFile;
import com.gll.shop.entity.common.ShopFileResp;
import com.gll.shop.service.shopFile.ShopFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    public FileUploadController(ShopFileService shopFileService) {
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
        ShopFileResp resp = ShopFileResp.convert(shopFile);
        return ResultContext.buildSuccess(null, resp);
    }

    @PostMapping("/getFileById")
    public ResultContext<ShopFileResp> getFileById(@RequestBody JSONObject param) throws IOException {
        Long id = param.getLong("id");
        ShopFile shopFile = shopFileService.getById(id);
        return ResultContext.buildSuccess(null, ShopFileResp.convert(shopFile));
    }


}
