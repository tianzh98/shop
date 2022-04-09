package com.gll.shop.controller.common;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.common.SortedTableFieldConfigDTO;
import com.gll.shop.service.common.TableConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gaoll
 * @date 2022年04月05日
 */
@RestController
@RequestMapping("/tableConfig")
@Slf4j
public class TableConfigController {

    private final TableConfigService tableConfigService;

    @Autowired
    public TableConfigController(TableConfigService tableConfigService) {
        this.tableConfigService = tableConfigService;
    }

    @SaCheckLogin
    @PostMapping("/getSortedFieldConfig")
    public ResultContext<SortedTableFieldConfigDTO> getSortedFieldConfig(@RequestBody Map<String, String> param) {
        String tablePageName = param.get("tablePageName");
        return ResultContext.buildSuccess(null, tableConfigService.getSortedFieldConfig(tablePageName));
    }
}
