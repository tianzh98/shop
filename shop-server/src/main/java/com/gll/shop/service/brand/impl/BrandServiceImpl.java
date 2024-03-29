package com.gll.shop.service.brand.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.Brand;
import com.gll.shop.entity.BrandParam;
import com.gll.shop.mapper.BrandMapper;
import com.gll.shop.service.brand.BrandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 高up
* @description 针对表【brand(品牌表)】的数据库操作Service实现
* @createDate 2022-04-14 00:39:59
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

    public  final  BrandMapper brandMapper;

    public BrandServiceImpl(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    //获取下拉品牌
    @Override
    public ResultContext<List<DropDownDTO>> getBrands() {
        Map<Long,String> brandList = brandMapper.selectList(Wrappers.<Brand>lambdaQuery())
                .stream().distinct().collect(Collectors.toMap(Brand::getId,Brand::getName));
      List<DropDownDTO> dropDownDTOS = new ArrayList<>();
        for(Map.Entry<Long,String> entry:brandList.entrySet())
        {
             DropDownDTO downDTO = new DropDownDTO();
             downDTO.setValue(String.valueOf(entry.getKey()));
             downDTO.setLabel(String.valueOf(entry.getValue()));
             dropDownDTOS.add(downDTO);
        }
        return ResultContext.buildSuccess("成功获取品牌",dropDownDTOS);
    }

    @Override
    public ResultContext<IPage<Brand>> getBrandDetail(BrandParam param) {
        // 构造分页条件：查询第几页，每页多少条数据
        Page<Brand> page = new Page<>(param.getPageNum(),param.getPageSize());
        page = brandMapper.selectPage(page,Wrappers.<Brand>lambdaQuery()
                .eq(param.getId() != null,Brand::getId,param.getId()));
        return ResultContext.buildSuccess("品牌分页查询成功",page);
    }

    @Override
    public ResultContext<Void> insertAndUpdateBrand(Brand brand) {
        int  result;
        Long id = brand.getId();
       //判断id是否为null
        //插入品牌
        if(null == id)
        {
            result =  brandMapper.insert(brand);
        }else
        {
            //更新品牌
            result = brandMapper.updateById(brand);
        }
      if(result <= 0)
          throw new RuntimeException("插入或者更新brand错误");
        return  ResultContext.buildSuccess("插入或者更新品牌成功",null);
    }

    @Override
    public ResultContext<Void> deleteBrandById(Integer id) {
      int result =  brandMapper.deleteById(id);
        if(result <= 0)
            throw new RuntimeException("删除brand错误");
        return  ResultContext.buildSuccess("删除成功",null);
    }

    @Override
    public ResultContext<Brand> getBrandById(Long id) {
        Brand brand = brandMapper.selectById(id);
        return ResultContext.buildSuccess("成功返回品牌",brand);
    }

    @Override
    public ResultContext<Void> editBrand(Brand brand) {
        int result = brandMapper.updateById(brand);
        if(result <= 0)
            throw new RuntimeException("编辑brand错误");
        return  ResultContext.buildSuccess("编辑",null);
    }


}




