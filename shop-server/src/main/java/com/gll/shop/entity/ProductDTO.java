package com.gll.shop.entity;

import com.gll.shop.entity.common.ShopFileResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * @author gaoll
 * @date 2022年04月19日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDTO extends Product {
    private static final long serialVersionUID = 7434540731219370953L;

    private List<Long> oldPicIdList;

    private List<Long> newPicIdList;

    /**
     * 商品规格
     */
    private List<ProductAttributeVO> selectProductAttr;

    /**
     * 商品参数
     */
    private List<ProductAttributeVO> selectProductParam;

    /**
     * 库存信息
     */
    private List<ProductStock> stockList;

    /**
     * 上架状态 翻译 展示
     */
    private String publishStatusShow;

    private ShopFileResp coverPic;

}
