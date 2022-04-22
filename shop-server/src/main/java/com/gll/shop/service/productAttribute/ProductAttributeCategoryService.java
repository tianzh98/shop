package com.gll.shop.service.productAttribute;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductAttributeCategory;
import com.gll.shop.entity.common.BaseQueryParams;

/**
 *
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    ResultContext<IPage<ProductAttributeCategory>> getProductAttributeList(BaseQueryParams params);

    ResultContext<ProductAttributeCategory> getProductAttributeCategoryById(Long id);

    ResultContext<Void> deleteProductAttributeCategoryById(Long id);

    ResultContext<Void> insertAndUpdateProductAttributeCategoryById(ProductAttributeCategory productAttributeCategory);
}
