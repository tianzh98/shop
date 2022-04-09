package com.gll.shop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;
import com.gll.shop.service.product.ProductService;
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
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/queryProductList")
    public ResultContext<IPage<ProductDTO>> queryProductList(@RequestBody ProductParam param) {
        return productService.queryProductList(param);
    }

}
