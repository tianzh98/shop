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
import com.gll.shop.service.brand.BrandService;
import com.gll.shop.service.productManager.ProductCategoryService;
import com.gll.shop.service.productManager.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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


    @PostMapping("/getBrands")
    public ResultContext<List<DropDownDTO>> getBrands() {
        return brandService.getBrands();
    }

    @PostMapping("/getProductCategories")
    public ResultContext<List<DropDownDTO>> getProductCategories() {
        return productCategoryService.getProductCategory();
    }

    @PostMapping("/editProductCateDetail")
    public ResultContext<String> editProductCateDetail(@RequestBody ProductCategory param) {
        if (param.getParentId() == 0) {
            param.setLevel(1);
        } else {
            param.setLevel(2);
        }
        if (param.getId() == null) {
            productCategoryService.save(param);
        } else {
            productCategoryService.updateById(param);
        }
        return ResultContext.success(null);
    }


    @PostMapping("/getParentDropdown")
    public ResultContext<List<DropDownDTO>> getParentDropdown() {

        // 获取所有的一级分类
        List<ProductCategory> productCategoryList = productCategoryService.list(Wrappers.<ProductCategory>lambdaQuery()
                .eq(ProductCategory::getLevel, 1));

        List<DropDownDTO> list = new ArrayList<>();
        // 构造默认（无上级分类）
        DropDownDTO defaultDropDownDTO = new DropDownDTO();
        defaultDropDownDTO.setLabel("无上级分类");
        defaultDropDownDTO.setValue("0");
        defaultDropDownDTO.setChildren(null);
        list.add(defaultDropDownDTO);
        // 转成DropDownDTO对象
        List<DropDownDTO> dropDownDTOList = productCategoryList.stream().map(productCategory -> {
            DropDownDTO dropDownDTO = new DropDownDTO();
            dropDownDTO.setLabel(productCategory.getName());
            dropDownDTO.setValue(String.valueOf(productCategory.getId()));
            dropDownDTO.setChildren(null);
            return dropDownDTO;
        }).collect(Collectors.toList());
        list.addAll(dropDownDTOList);
        return ResultContext.buildSuccess(null, list);
    }

}
