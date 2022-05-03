package com.gll.shop.service.common;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gll.shop.common.enums.ENYesOrNo;
import com.gll.shop.entity.SysDefaultTableConfig;
import com.gll.shop.entity.common.SortedTableFieldConfigDTO;
import com.gll.shop.mapper.SysDefaultTableConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaoll
 * @date 2022年04月05日
 */
@Service
public class TableConfigService {

    private final SysDefaultTableConfigMapper sysDefaultTableConfigMapper;

    @Autowired
    public TableConfigService(SysDefaultTableConfigMapper sysDefaultTableConfigMapper) {
        this.sysDefaultTableConfigMapper = sysDefaultTableConfigMapper;
    }

    public SortedTableFieldConfigDTO getSortedFieldConfig(String tablePageName) {
        List<SysDefaultTableConfig> sysDefaultTableConfigList = sysDefaultTableConfigMapper.selectList(Wrappers.<SysDefaultTableConfig>lambdaQuery()
                .eq(SysDefaultTableConfig::getTablePageName, tablePageName)
                .orderByAsc(SysDefaultTableConfig::getSortNum));

        List<String> showFields = new ArrayList<>();
        List<String> notShowFields = new ArrayList<>();
        Map<String, String> fieldNameDictionary = new HashMap<>();
        Map<String, String> fieldTypeDictionary = new HashMap<>();

        Map<String, String> orderByDictionary = new HashMap<>();
        Map<String, String> userCustomizeWidthDictionary = new HashMap<>();
        sysDefaultTableConfigList.forEach(sysDefaultTableConfig -> {
            if (ENYesOrNo.isYes(sysDefaultTableConfig.getIsShow())) {
                showFields.add(sysDefaultTableConfig.getFieldName());
            } else {
                notShowFields.add(sysDefaultTableConfig.getFieldName());
            }
            fieldNameDictionary.put(sysDefaultTableConfig.getFieldName(), sysDefaultTableConfig.getShowName());
            fieldTypeDictionary.put(sysDefaultTableConfig.getFieldName(), sysDefaultTableConfig.getType());
            if (StrUtil.isNotBlank(sysDefaultTableConfig.getOrderBy())) {
                orderByDictionary.put(sysDefaultTableConfig.getFieldName(), sysDefaultTableConfig.getOrderBy());
            }
        });
        SortedTableFieldConfigDTO sortedTableFieldConfigDTO = new SortedTableFieldConfigDTO();
        sortedTableFieldConfigDTO.setShowFields(showFields);
        sortedTableFieldConfigDTO.setNotShowFields(notShowFields);
        sortedTableFieldConfigDTO.setFieldNameDictionary(fieldNameDictionary);
        sortedTableFieldConfigDTO.setFieldTypeDictionary(fieldTypeDictionary);
        sortedTableFieldConfigDTO.setOrderByDictionary(orderByDictionary);
        sortedTableFieldConfigDTO.setUserId(StpUtil.getLoginIdAsString());
        sortedTableFieldConfigDTO.setTablePageName(tablePageName);
        sortedTableFieldConfigDTO.setUserCustomizeWidthDictionary(userCustomizeWidthDictionary);
        return sortedTableFieldConfigDTO;

    }
}
