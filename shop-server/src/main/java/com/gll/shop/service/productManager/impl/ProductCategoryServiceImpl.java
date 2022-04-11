package com.gll.shop.service.productManager.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ProductCategory;
import com.gll.shop.entity.ProductCategoryDTO;
import com.gll.shop.entity.common.BaseQueryParams;
import com.gll.shop.mapper.ProductCategoryMapper;
import com.gll.shop.service.productManager.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
        implements ProductCategoryService {

    @Override
    public ResultContext<IPage<ProductCategoryDTO>> getProductCateList(BaseQueryParams param) {
        // 构造分页条件：查询第几页，每页多少条数据
        Page<ProductCategory> page = new Page<>(param.getPageNum(), param.getPageSize());
        // 先查出一级分类
        Page<ProductCategory> oneLevelProductCategoryPage = getBaseMapper().selectPage(page, Wrappers.<ProductCategory>lambdaQuery()
                .eq(ProductCategory::getLevel, 1));


        return ResultContext.buildSuccess(null, oneLevelProductCategoryPage.convert(this::convertByLevelAndTranslate));
    }

    private ProductCategoryDTO convertByLevelAndTranslate(ProductCategory po) {

        ProductCategoryDTO dto = this.translate(po);
        // 把二级分类查询出来 设置进去
        List<ProductCategory> productCategories = getBaseMapper().selectList(Wrappers.<ProductCategory>lambdaQuery()
                .eq(ProductCategory::getParentId, dto.getId())
                .eq(ProductCategory::getLevel, 2));
        List<ProductCategoryDTO> collect = productCategories
                .stream()
                .map(this::translate)
                .collect(Collectors.toList());
        dto.setChildren(collect);
        return dto;
    }

    private ProductCategoryDTO translate(ProductCategory po) {
        ProductCategoryDTO dto = new ProductCategoryDTO();
        BeanUtil.copyProperties(po, dto);
        dto.setLevelShow(po.getLevel() == 1 ? "一级" : (po.getLevel() == 2 ? "二级" : "未知"));
        return dto;
    }

}




