package com.gll.shop.controller.admin.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.*;
import com.gll.shop.entity.common.BaseQueryParams;
import com.gll.shop.service.ProductAttributeCategoryService;
import com.gll.shop.service.ProductAttributeService;
import com.gll.shop.service.ProductAttributeValueService;
import com.gll.shop.service.ProductStockService;
import com.gll.shop.service.productManager.ProductCategoryService;
import com.gll.shop.service.productManager.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final ProductAttributeCategoryService productAttributeCategoryService;
    private final ProductAttributeService productAttributeService;
    private final ProductStockService productStockService;
    private final ProductAttributeValueService productAttributeValueService;

    //构造方法注入
    public ProductController(ProductService productService, ProductCategoryService productCategoryService,
                             ProductAttributeCategoryService productAttributeCategoryService,
                             ProductAttributeService productAttributeService,
                             ProductStockService productStockService,
                             ProductAttributeValueService productAttributeValueService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.productAttributeCategoryService = productAttributeCategoryService;
        this.productAttributeService = productAttributeService;
        this.productStockService = productStockService;
        this.productAttributeValueService = productAttributeValueService;
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


    @PostMapping("/getProductAttributeCategoryDropDown")
    public ResultContext<List<DropDownDTO>> getProductAttributeCategoryDropDown() {
        List<DropDownDTO> collect = productAttributeCategoryService.list()
                .stream().map(productAttributeCategory -> {
                    DropDownDTO dropDownDTO = new DropDownDTO();
                    dropDownDTO.setLabel(productAttributeCategory.getName());
                    dropDownDTO.setValue(String.valueOf(productAttributeCategory.getId()));
                    dropDownDTO.setChildren(null);
                    return dropDownDTO;
                }).collect(Collectors.toList());
        // 获取商品属性类别的下拉框
        return ResultContext.buildSuccess(null, collect);
    }

    @PostMapping("/getProductAttribute")
    public ResultContext<List<ProductAttribute>> getProductAttribute(@RequestBody Map<String, Long> params) {
        Long productAttributeCategoryId = params.get("productAttributeCategoryId");
        List<ProductAttribute> productAttributeList = productAttributeService.list(Wrappers.<ProductAttribute>lambdaQuery()
                .eq(ProductAttribute::getProductAttributeCategoryId, productAttributeCategoryId));
        // 获取商品属性类别的下拉框
        return ResultContext.buildSuccess(null, productAttributeList);
    }


    @PostMapping("/getSkuStockList")
    public ResultContext<List<ProductStock>> getSkuStockList(@RequestBody Map<String, Long> params) {
        Long productId = params.get("productId");
        List<ProductStock> list = productStockService.list(Wrappers.<ProductStock>lambdaQuery()
                .eq(ProductStock::getProductId, productId));
        // 获取商品属性类别的下拉框
        return ResultContext.buildSuccess(null, list);
    }


    // 获取商品具体属性值
    @PostMapping("/getProductAttributeValueList")
    public ResultContext<List<ProductAttributeValue>> getProductAttributeValueList(@RequestBody Map<String, Long> params) {
        Long productId = params.get("productId");
        List<ProductAttributeValue> list = productAttributeValueService.list(Wrappers.<ProductAttributeValue>lambdaQuery()
                .eq(ProductAttributeValue::getProductId, productId));
        // 获取商品属性类别的下拉框
        return ResultContext.buildSuccess(null, list);
    }


}
