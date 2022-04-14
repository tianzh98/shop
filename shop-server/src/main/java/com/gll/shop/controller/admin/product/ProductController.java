package com.gll.shop.controller.admin.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.ProductCategoryDTO;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;
import com.gll.shop.entity.common.BaseQueryParams;
import com.gll.shop.service.brand.BrandService;
import com.gll.shop.service.productManager.ProductCategoryService;
import com.gll.shop.service.productManager.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaoll
 * @date 2022年04月05日
 */
@RestController
@RequestMapping("/product")
@Slf4j
//下面的每一个方法都会检查是否登录
@SaCheckLogin
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final BrandService brandService;

    //构造方法注入
    public ProductController(ProductService productService, ProductCategoryService productCategoryService, BrandService brandService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.brandService = brandService;
    }

    @PostMapping("/getProductList")
    public ResultContext<IPage<ProductDTO>> getProductList(@RequestBody ProductParam param) {
        return productService.queryProductList(param);
    }

    @PostMapping("/getProductCateList")
    public ResultContext<IPage<ProductCategoryDTO>> getProductCateList(@RequestBody BaseQueryParams param) {
        return productCategoryService.getProductCateList(param);
    }
    @PostMapping("/getBrands")
    public  ResultContext<List<DropDownDTO>> getBrands()
    {
        return brandService.getBrands();
    }
    @PostMapping("/getProductCategories")
    public  ResultContext<List<DropDownDTO>> getProductCategories()
    {
        return productCategoryService.getProductCategory();
    }

}
