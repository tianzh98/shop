package com.gll.shop.service.productManager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.ProductCategory;
import com.gll.shop.entity.ProductCategoryDTO;
import com.gll.shop.entity.common.BaseQueryParams;

import java.util.List;

/**
 *
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    ResultContext<IPage<ProductCategoryDTO>> getProductCateList(BaseQueryParams param);
    ResultContext<List<DropDownDTO>> getProductCategory();
}
