package com.gll.shop.service.productAttribute;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductAttribute;
import com.gll.shop.entity.ProductAttributeDTO;
import com.gll.shop.entity.ProductAttributeParam;

import java.util.List;

/**
 *
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    ResultContext<List<ProductAttributeDTO>> getProductAttributeParam(ProductAttributeParam param);

    ResultContext<Void> insertOrUpdateProductAttribute(ProductAttribute productAttribute);

    ResultContext<Void> deleteProductAttributeById(Long id);
}
