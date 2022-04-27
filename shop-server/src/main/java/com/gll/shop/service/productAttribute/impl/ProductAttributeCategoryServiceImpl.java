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

    @Override
    public ResultContext<ProductAttributeCategory> getProductAttributeCategoryById(Long id) {
        ProductAttributeCategory productAttributeCategory = getBaseMapper().selectById(id);
        return ResultContext.buildSuccess("得到商品类型通过id",productAttributeCategory);
    }

    @Override
    public ResultContext<Void> deleteProductAttributeCategoryById(Long id) {
        int result = getBaseMapper().deleteById(id);
        if(result <= 0)
        {
            ResultContext.businessFail("删除商品类型错误");
        }
        return ResultContext.buildSuccess("删除商品类型成功",null);
    }

    @Override
    public ResultContext<Void> insertAndUpdateProductAttributeCategoryById(ProductAttributeCategory productAttributeCategory) {
        Long id = productAttributeCategory.getId();
        int result ;
        if(null == id)
        {
            //商品属性和商品参数默认设置为0
            productAttributeCategory.setAttributeCount(0);
            productAttributeCategory.setParamCount(0);
            result = getBaseMapper().insert(productAttributeCategory);
        }else
        {
            result = getBaseMapper().updateById(productAttributeCategory);
        }
        if(result <= 0)
        {
          ResultContext.businessFail("删除或者更新商品类型页面错误");
        }
        return ResultContext.buildSuccess("删除或者更新商品类型页面成功",null);
    }
}




