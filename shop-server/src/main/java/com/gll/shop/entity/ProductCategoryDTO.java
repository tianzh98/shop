package com.gll.shop.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gaoll
 * @date 2022年04月10日
 */
@Setter
@Getter
public class ProductCategoryDTO extends ProductCategory {
    private static final long serialVersionUID = 8741602060125770491L;
    private String levelShow;
    List<ProductCategoryDTO> children;
}
