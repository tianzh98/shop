package com.gll.shop.service.productAttribute.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.enums.ENSelectType;
import com.gll.shop.entity.ProductAttribute;
import com.gll.shop.entity.ProductAttributeDTO;
import com.gll.shop.entity.ProductAttributePO;
import com.gll.shop.entity.ProductAttributeParam;
import com.gll.shop.mapper.ProductAttributeMapper;
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

    @Override
    public ResultContext<List<ProductAttributeDTO>> getProductAttributeParam(ProductAttributeParam param) {
        List<ProductAttributeDTO> list = getBaseMapper().getProductTypeParam(param)
                .stream().distinct().map(this::translation).collect(Collectors.toList());
        return ResultContext.buildSuccess("查询商品类型参数成功",list);
    }

    private ProductAttributeDTO translation(ProductAttributePO productAttributePO) {
        ProductAttributeDTO dto = new ProductAttributeDTO();
        BeanUtil.copyProperties(productAttributePO,dto);
        dto.setSelectTypeName(ENSelectType.getLabelByValue(String.valueOf(dto.getSelectType())));
        return dto;
    }
}




