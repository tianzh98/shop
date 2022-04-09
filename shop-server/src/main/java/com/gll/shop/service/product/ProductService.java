package com.gll.shop.service.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.Product;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;

/**
 *
 */
public interface ProductService extends IService<Product> {

    ResultContext<IPage<ProductDTO>> queryProductList(ProductParam param);

}
