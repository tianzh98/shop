package com.gll.shop.controller.admin.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductCategoryDTO;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;
import com.gll.shop.entity.common.BaseQueryParams;
import com.gll.shop.service.productManager.ProductCategoryService;
import com.gll.shop.service.productManager.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoll
 * @date 2022年04月05日
 */
@RestController
@RequestMapping("/product")
@Slf4j
@SaCheckLogin
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/getProductList")
    public ResultContext<IPage<ProductDTO>> getProductList(@RequestBody ProductParam param) {
        return productService.queryProductList(param);
    }

    @PostMapping("/getProductCateList")
    public ResultContext<IPage<ProductCategoryDTO>> getProductCateList(@RequestBody BaseQueryParams param) {
        return productCategoryService.getProductCateList(param);
    }
}
