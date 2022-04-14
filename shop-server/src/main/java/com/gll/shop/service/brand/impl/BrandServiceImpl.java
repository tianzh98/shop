package com.gll.shop.service.brand.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.Brand;
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
}




