package com.gll.shop.service.productManager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.Product;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;

import java.util.List;

/**
 *
 */
public interface ProductService extends IService<Product> {

    ResultContext<IPage<ProductDTO>> queryProductList(ProductParam param);

    ResultContext<String> saveOrUpdateProductDetail(ProductDTO productDTO);

    ResultContext<String> deleteProductByIds(List<Long> idList);

    ResultContext<String> updateProduct(List<Product> productList);

}
