package com.gll.shop.service.order.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.common.enums.ENPayType;
import com.gll.shop.common.enums.ENStatus;
import com.gll.shop.entity.Order;
import com.gll.shop.entity.OrderParam;
import com.gll.shop.service.order.OrderService;
import com.gll.shop.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 高up
* @description 针对表【order(订单表)】的数据库操作Service实现
* @createDate 2022-04-18 14:26:40
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    public final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public ResultContext<IPage<Order>> getOrderList(OrderParam param) {
        Page<Order> page = new Page<>(param.getPageNum(),param.getPageSize());
        page = orderMapper.selectPage(page, Wrappers.<Order>lambdaQuery()
                .eq(StrUtil.isNotBlank(param.getOrderSn()) ,Order::getOrderSn,param.getOrderSn())
                .eq(StrUtil.isNotBlank(param.getMemberUsername()),Order::getMemberUsername,param.getMemberUsername())
                .eq(null != param.getPayType(),Order::getPayType,param.getPayType())
                .eq(null != param.getStatus(),Order::getStatus,param.getStatus())
        );
        return ResultContext.buildSuccess("查询订单列表成功",page);
    }

    @Override
    public ResultContext<List<DropDownDTO>> getPayType() {
        List<DropDownDTO> dropDownDTOS = new ArrayList<>();
        //遍历支付方式枚举类
        for(ENPayType enPayType:ENPayType.values())
        {
            DropDownDTO downDTO = new DropDownDTO();
            downDTO.setValue(enPayType.getValue());
            downDTO.setLabel(enPayType.getLabel());
            dropDownDTOS.add(downDTO);
        }
        return ResultContext.buildSuccess("成功获取支付方式下拉",dropDownDTOS);
    }

    @Override
    public ResultContext<List<DropDownDTO>> getStatus() {
        List<DropDownDTO> dropDownDTOS = new ArrayList<>();
        //遍历支付状态枚举类
        for(ENStatus enStatus:ENStatus.values())
        {
            DropDownDTO downDTO = new DropDownDTO();
            downDTO.setValue(enStatus.getValue());
            downDTO.setLabel(enStatus.getLabel());
            dropDownDTOS.add(downDTO);
        }
        return ResultContext.buildSuccess("成功获取支付状态下拉",dropDownDTOS);
    }
}




