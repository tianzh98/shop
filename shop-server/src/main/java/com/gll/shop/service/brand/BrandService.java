package com.gll.shop.service.brand;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.Brand;

import java.util.List;

/**
* @author 高up
* @description 针对表【brand(品牌表)】的数据库操作Service
* @createDate 2022-04-14 00:39:59
*/
public interface BrandService extends IService<Brand> {
    ResultContext<List<DropDownDTO>> getBrands();

}