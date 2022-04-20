package com.gll.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.util.List;

/**
 * 用于添加、修改商品
 *
 * @author gaoll
 * @date 2022年04月19日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDetail extends Product {
    private static final long serialVersionUID = 7434540731219370953L;


    private List<File> picFiles;

    private List<ProductAttributeVO> selectProductAttr;

    private List<ProductAttributeVO> selectProductParam;

    private List<ProductStock> stockList;


}
