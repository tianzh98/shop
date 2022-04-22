package com.gll.shop.controller.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.Brand;
import com.gll.shop.entity.BrandParam;
import com.gll.shop.service.brand.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
@Slf4j
//下面的每一个方法都会检查是否登录
@SaCheckLogin
public class BrandController {
    public final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/getBrands")
    public ResultContext<List<DropDownDTO>> getBrands() {
        return brandService.getBrands();
    }

    @PostMapping("/getBrandDetail")
    public ResultContext<IPage<Brand>> getBrandAll(@RequestBody BrandParam param) {
        return brandService.getBrandDetail(param);
    }
    @PostMapping("/insertBrand")
    public ResultContext<Void> insertAndUpdateBrand(@RequestBody Brand brand) {
        return brandService.insertAndUpdateBrand(brand);
    }
    @PostMapping("/deleteBrandById")
    public ResultContext<Void> deleteBrandById(@RequestBody JSONObject param) {
        Integer id = param.getInt("id");
        return brandService.deleteBrandById(id);
    }
    @PostMapping("/getBrandById")
    public ResultContext<Brand> getBrandById(@RequestBody  Map<String,Long> map)
    {
        Long id = map.get("id");
       return  brandService.getBrandById(id);
    }
    @PostMapping("/editBrand")
    public ResultContext<Void> editBrand(Brand brand)
    {
        return  brandService.editBrand(brand);
    }
}


