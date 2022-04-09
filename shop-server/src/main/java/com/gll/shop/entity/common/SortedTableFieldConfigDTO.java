package com.gll.shop.entity.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author gaoll
 * @date 2022年04月05日
 */
@Data
public class SortedTableFieldConfigDTO implements Serializable {

    private static final long serialVersionUID = -5994836623915924658L;


    /**
     * 需要显示的、已经排好序的字段名，英文；
     */
    private List<String> showFields;


    /**
     * 不需要显示的字段名，英文；
     */
    private List<String> notShowFields;


    /**
     * 字段名字典：英文名->页面显示的中文名
     */
    private Map<String, String> fieldNameDictionary;


    /**
     * 前端点击列名的排序字段名称，和mybatis查询语句的字段别名对应。字段为空或无改值时，表示该字段不支持排序
     */
    private Map<String, String> orderByDictionary;


    /**
     * 用户urid。服务端可以从UserContext中取
     */
    private String userId;


    /**
     * 表格页面名字。
     */
    private String tablePageName;


    /**
     * 前端字段进行渲染时用的字段宽度，vlaue={width:number}
     */
    private Map<String, String> userCustomizeWidthDictionary;

}