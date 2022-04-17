package com.gll.shop.service.productManager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.Product;
import com.gll.shop.entity.ProductDTO;
import com.gll.shop.entity.ProductParam;
import com.gll.shop.mapper.ProductMapper;
import com.gll.shop.service.productManager.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {


    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ResultContext<IPage<ProductDTO>> queryProductList(ProductParam param) {
        // 构造分页条件：查询第几页，每页多少条数据
        Page<Product> page = new Page<>(param.getPageNum(), param.getPageSize());

        page = productMapper.selectPage(page, Wrappers.<Product>lambdaQuery()
                .eq(param.getId() != null, Product::getId,param.getId())
                .eq(null != param.getBrandId(), Product::getBrandId, param.getBrandId())
                .eq(null != param.getProductCategoryId(), Product::getProductCategoryId, param.getProductCategoryId())
                .eq(StrUtil.isNotBlank(param.getProductSn()), Product::getProductSn, param.getProductSn())
                .like(StrUtil.isNotBlank(param.getName()), Product::getName, param.getName()));

        return ResultContext.buildSuccess(null, page.convert(this::translate));
    }

    //翻译，把product转换为productDTO
    private ProductDTO translate(Product po) {
        ProductDTO dto = new ProductDTO();
        BeanUtil.copyProperties(po, dto);
        return dto;
    }


}




