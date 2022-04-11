package com.gll.shop.controller.common;

import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.auth.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoll
 * @date 2022年04月10日
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileUploadController {

    @PostMapping("/upload")
    public ResultContext<LoginResult> upload() {

        return ResultContext.success(null);
    }
}
