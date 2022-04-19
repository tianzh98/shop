package com.gll.shop.service.productAttribute.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductAttributeCategory;
import com.gll.shop.entity.common.BaseQueryParams;
import com.gll.shop.mapper.ProductAttributeCategoryMapper;
import com.gll.shop.service.productAttribute.ProductAttributeCategoryService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory>
    implements ProductAttributeCategoryService {

    @Override
    public ResultContext<IPage<ProductAttributeCategory>> getProductAttributeList(BaseQueryParams params) {
        IPage<ProductAttributeCategory> page = new Page<>(params.getPageNum(),params.getPageSize());
        page = getBaseMapper().selectPage(page, Wrappers.<ProductAttributeCategory>lambdaQuery());
        return ResultContext.buildSuccess("返回商品分类成功",page);
    }
}




