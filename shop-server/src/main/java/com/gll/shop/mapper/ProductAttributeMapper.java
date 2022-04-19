package com.gll.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.shop.entity.ProductAttribute;
import com.gll.shop.entity.ProductAttributePO;
import com.gll.shop.entity.ProductAttributeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.gll.shop.entity.ProductAttribute
 */
public interface ProductAttributeMapper extends BaseMapper<ProductAttribute> {
       List<ProductAttributePO> getProductTypeParam(@Param("param") ProductAttributeParam param);
}




