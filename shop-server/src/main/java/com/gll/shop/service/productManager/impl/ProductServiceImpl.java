package com.gll.shop.service.productManager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.enums.ENProductAttributeType;
import com.gll.shop.common.enums.ENYesOrNo;
import com.gll.shop.common.utils.ConvertUtil;
import com.gll.shop.entity.*;
import com.gll.shop.entity.common.ShopFileResp;
import com.gll.shop.mapper.*;
import com.gll.shop.service.productManager.ProductService;
import com.gll.shop.service.shopFile.ShopFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 *
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {


    private final ProductMapper productMapper;
    private final ShopFileMapper shopFileMapper;
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductStockMapper productStockMapper;
    private final BrandMapper brandMapper;

    private final ShopFileService shopFileService;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper,
                              ShopFileMapper shopFileMapper,
                              ProductAttributeValueMapper productAttributeValueMapper,
                              ProductStockMapper productStockMapper,
                              BrandMapper brandMapper,
                              ShopFileService shopFileService) {
        this.productMapper = productMapper;
        this.shopFileMapper = shopFileMapper;
        this.productAttributeValueMapper = productAttributeValueMapper;
        this.productStockMapper = productStockMapper;
        this.brandMapper = brandMapper;
        this.shopFileService = shopFileService;
    }

    @Override
    public ResultContext<IPage<ProductDTO>> queryProductList(ProductParam param) {
        // 构造分页条件：查询第几页，每页多少条数据
        Page<Product> page = new Page<>(param.getPageNum(), param.getPageSize());

        page = productMapper.selectPage(page, Wrappers.<Product>lambdaQuery()
                .eq(param.getId() != null, Product::getId, param.getId())
                .eq(null != param.getBrandId(), Product::getBrandId, param.getBrandId())
                .eq(null != param.getProductCategoryId(), Product::getProductCategoryId, param.getProductCategoryId())
                .eq(StrUtil.isNotBlank(param.getProductSn()), Product::getProductSn, param.getProductSn())
                .eq(null != param.getPublishStatus(), Product::getPublishStatus, param.getPublishStatus())
                .like(StrUtil.isNotBlank(param.getName()), Product::getName, param.getName()));

        return ResultContext.buildSuccess(null, ConvertUtil.convert(page, this::translate));
    }

    //翻译，把product转换为productDTO
    private ProductDTO translate(Product po) {
        ProductDTO dto = new ProductDTO();
        BeanUtil.copyProperties(po, dto);
        if (StrUtil.isNotBlank(po.getPicIdListStr())) {
            List<String> idListStr = Arrays.asList(po.getPicIdListStr().split(StrUtil.COMMA));
            dto.setOldPicIdList(idListStr.stream().map(Long::valueOf).collect(Collectors.toList()));


//            ShopFile shopFile = shopFileMapper.selectById(Long.valueOf(idListStr.get(0)));

            dto.setCoverPic(shopFileService.getFileById(Long.valueOf(idListStr.get(0))));
        }
        dto.setPublishStatusShow(ENYesOrNo.isYes(po.getPublishStatus()) ? "已上架" : "未上架");

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResultContext<String> saveOrUpdateProductDetail(ProductDTO productDTO) {
        // 处理图片，首先获取 oldPicIdList 和 newPicIdList
        List<Long> oldPicIdList = productDTO.getOldPicIdList() == null ? new ArrayList<>() : productDTO.getOldPicIdList();
        List<Long> newPicIdList = productDTO.getNewPicIdList() == null ? new ArrayList<>() : productDTO.getNewPicIdList();

        // 找出需要删除的图片，从oldPicIdList中找出 不在newPicIdList里的id
        List<Long> needToDeleteIdList =
                oldPicIdList.stream().filter(id -> !newPicIdList.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(needToDeleteIdList)) {
            shopFileMapper.deleteBatchIds(needToDeleteIdList);
        }

        // 设置商品图片
        productDTO.setPicIdListStr(CollectionUtil.join(newPicIdList, StrUtil.COMMA));
        // 默认上架状态
        if (null == productDTO.getPublishStatus()) {
            productDTO.setPublishStatus(ENYesOrNo.YES.getIntValue());
        }
        // 设置品牌名称
        if (StrUtil.isBlank(productDTO.getBrandName())) {
            productDTO.setBrandName(brandMapper.selectById(productDTO.getBrandId()).getName());
        }
        // todo 设置一些其他属性
        // 保存到product表
        if (ObjectUtil.isNotNull(productDTO.getId())) {
            int update = productMapper.updateById(productDTO);
            if (update != 1) {
                throw new RuntimeException("更新商品信息失败");
            }
        } else {
            // 插入成功，productDTO的id会被赋予插入数据库那条记录的id值
            int insert = productMapper.insert(productDTO);
            if (insert != 1) {
                throw new RuntimeException("保存商品信息失败");
            }
        }
        // 至此 productDTO 的id 是一定有值的。

        // 保存商品规格属性信息
        List<ProductAttributeVO> selectProductAttr = productDTO.getSelectProductAttr();
        List<ProductAttributeVO> selectProductParam = productDTO.getSelectProductParam();
        List<ProductAttributeVO> unionList = CollectionUtil.unionAll(selectProductAttr, selectProductParam);

        // 先删除原来的 productAttributeValue
        productAttributeValueMapper.delete(Wrappers.<ProductAttributeValue>lambdaQuery()
                .eq(ProductAttributeValue::getProductId, productDTO.getId()));
        // 准备插入新的productAttributeValue
        unionList.forEach(productAttributeVO -> {
            String value = null;
            if (ENProductAttributeType.ATTR.getValue().equals(productAttributeVO.getType())
                    && ENYesOrNo.isYes(String.valueOf(productAttributeVO.getHandAddStatus()))) {
                // 规格手工录入的可选值   取 options 逗号隔开
                value = CollectionUtil.join(productAttributeVO.getOptions(), StrUtil.COMMA);

            } else if (ENProductAttributeType.PARAM.getValue().equals(productAttributeVO.getType())) {
                // 商品参数，直接取value单值
                value = productAttributeVO.getValue();
            } // 其他情况不需要存储 ProductAttributeValue

            if (null != value) {
                ProductAttributeValue productAttributeValue = new ProductAttributeValue();
                productAttributeValue.setProductId(productDTO.getId());
                productAttributeValue.setProductAttributeId(productAttributeVO.getId());
                productAttributeValue.setValue(value);
                productAttributeValueMapper.insert(productAttributeValue);
            }
        });

        // 保存库存信息
        // 删除原来的库存信息
        // 采用先删除再插入的方式。这里不太好更新，因为前端可能会删除，你还得记录删除了哪些
        productStockMapper.delete(Wrappers.<ProductStock>lambdaQuery()
                .eq(ProductStock::getProductId, productDTO.getId()));
        List<ProductStock> stockList = productDTO.getStockList();
        if (CollectionUtil.isNotEmpty(stockList)) {
            stockList.forEach(productStock -> {
                productStock.setId(null);
                productStock.setProductId(productDTO.getId());
                // 如果用户没有填写商品编码，则自动生成
                if (StrUtil.isEmpty(productStock.getProductCode())) {
                    productStock.setProductCode(UUID.fastUUID().toString(true));
                }
                productStockMapper.insert(productStock);
            });
        }
        return ResultContext.success("保存成功！");
    }


    @Override
    @Transactional
    public ResultContext<String> deleteProductByIds(List<Long> idList) {
        if (CollectionUtil.isNotEmpty(idList)) {
            idList.forEach(this::deleteProductById);
        }
        return ResultContext.success("删除成功！");
    }


    private void deleteProductById(Long id) {
        // 先删除和商品有关的表，再删除商品表
        // 1. 删除shop_file 商品相册
        // 查询相册id列表
        String picIdListStr = productMapper.selectOne(Wrappers.<Product>lambdaQuery()
                // 只查询一个字段，提高查询性能效率
                .select(Product::getPicIdListStr)
                .eq(Product::getId, id)).getPicIdListStr();
        if (StrUtil.isNotBlank(picIdListStr)) {
            // 进行逗号分割，并且转成Long类型
            List<Long> picIdList = StrUtil.split(picIdListStr, StrUtil.COMMA)
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            shopFileMapper.deleteBatchIds(picIdList);
        }

        // 2. 删除product_attribute_value 商品属性规格
        productAttributeValueMapper.delete(Wrappers.<ProductAttributeValue>lambdaQuery()
                .eq(ProductAttributeValue::getProductId, id));

        // 3. 删除product_stock库存信息
        productStockMapper.delete(Wrappers.<ProductStock>lambdaQuery()
                .eq(ProductStock::getProductId, id));
        // 4. 删除 product 商品表
        productMapper.deleteById(id);
    }


    @Override
    public ResultContext<String> updateProduct(List<Product> productList) {
        if (CollectionUtil.isNotEmpty(productList)) {
            for (Product product : productList) {
                if (null != product.getId()) {
                    productMapper.updateById(product);
                } else {
                    log.error("更新商品时，参数中商品id不能为空");
                }
            }
        }
        return ResultContext.success("更新成功！");
    }
}
