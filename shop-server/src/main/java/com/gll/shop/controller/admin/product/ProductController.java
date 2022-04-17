package com.gll.shop.controller.admin.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.ProductCategory;
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

import java.util.List;
import java.util.Map;

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

    //构造方法注入
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

    @PostMapping("/getProductCateDetail")
    public ResultContext<ProductCategoryDTO> getProductCateDetail(@RequestBody Map<String, Long> param) {
        Long id = param.get("id");
        return productCategoryService.getCateDetail(id);
    }

    @PostMapping("/deleteProductCateById")
    public ResultContext<String> deleteProductCateById(@RequestBody List<Long> idList) {
        productCategoryService.remove(Wrappers.<ProductCategory>lambdaQuery()
                .in(ProductCategory::getId, idList)
                .or()
                .in(ProductCategory::getParentId, idList));
        return ResultContext.success("删除成功！");
    }

    @PostMapping("/getProductCategories")
    public ResultContext<List<DropDownDTO>> getProductCategories() {
        return productCategoryService.getProductCategory();
    }

    @PostMapping("/editProductCateDetail")
    public ResultContext<String> editProductCateDetail(@RequestBody ProductCategory param) {
        return productCategoryService.editProductCateDetail(param);
    }

    @PostMapping("/getParentDropdown")
    public ResultContext<List<DropDownDTO>> getParentDropdown() {
       return productCategoryService.getParentDropdown();
    }

}
