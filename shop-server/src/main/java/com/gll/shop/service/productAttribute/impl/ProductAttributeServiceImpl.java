package com.gll.shop.service.productAttribute.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.enums.ENSelectType;
import com.gll.shop.entity.*;
import com.gll.shop.mapper.ProductAttributeMapper;
import com.gll.shop.service.productAttribute.ProductAttributeCategoryService;
import com.gll.shop.service.productAttribute.ProductAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute>
        implements ProductAttributeService {
    public final ProductAttributeCategoryService productAttributeCategoryService;

    public ProductAttributeServiceImpl(ProductAttributeCategoryService productAttributeCategoryService) {
        this.productAttributeCategoryService = productAttributeCategoryService;
    }


    @Override
    public ResultContext<List<ProductAttributeDTO>> getProductAttributeParam(ProductAttributeParam param) {
        List<ProductAttributeDTO> list = getBaseMapper().getProductTypeParam(param)
                .stream().distinct().map(this::translation).collect(Collectors.toList());
        return ResultContext.buildSuccess("查询商品类型参数成功", list);
    }

    @Override
    public ResultContext<Void> insertOrUpdateProductAttribute(ProductAttribute productAttribute) {
        //获取id
        Long id = productAttribute.getId();
        if (null == id) {
            //插入
            int result = this.getBaseMapper().insert(productAttribute);
            if (result <= 0)
            {return ResultContext.businessFail("插入商品类型参数错误");}
            ProductAttributeCategory productAttributeCategory = productAttributeCategoryService.getBaseMapper()
                    .selectOne(Wrappers.<ProductAttributeCategory>lambdaQuery().eq(ProductAttributeCategory::getId, productAttribute.getProductAttributeCategoryId()));
            if (productAttribute.getType() == 0) {
                //属性的类型；0->规格；1->参数
                productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
            } else {
               productAttributeCategory.setParamCount(productAttributeCategory.getParamCount()+1);
            }
             //插入数据库
            productAttributeCategoryService.getBaseMapper().updateById(productAttributeCategory);
        } else {
            //更新
            int result = this.getBaseMapper().updateById(productAttribute);
            if (result <= 0)
                return ResultContext.businessFail("更新商品类型参数错误");
        }
        return ResultContext.success("成功插入或者增加商品参数类型");
    }

    @Override
    public ResultContext<Void> deleteProductAttributeById(Long id) {
        int result = this.getBaseMapper().deleteById(id);
        if (result <= 0) {
            return ResultContext.businessFail("失败删除商品类型参数");
        }
        return ResultContext.success("成功删除商品类型参数");
    }

    @Override
    public ResultContext<ProductAttribute> getProductAttributeById(Long id) {
        return ResultContext.buildSuccess("成功得到商品类型参数",getBaseMapper().selectById(id));
    }

    private ProductAttributeDTO translation(ProductAttributePO productAttributePO) {
        ProductAttributeDTO dto = new ProductAttributeDTO();
        BeanUtil.copyProperties(productAttributePO, dto);
        dto.setSelectTypeName(ENSelectType.getLabelByValue(String.valueOf(dto.getSelectType())));
        return dto;
    }
}




