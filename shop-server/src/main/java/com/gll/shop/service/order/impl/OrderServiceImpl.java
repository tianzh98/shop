package com.gll.shop.service.order.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.common.enums.ENPayType;
import com.gll.shop.common.enums.ENStatus;
import com.gll.shop.entity.*;
import com.gll.shop.mapper.OrderItemMapper;
import com.gll.shop.service.order.OrderService;
import com.gll.shop.mapper.OrderMapper;
import com.gll.shop.service.shopFile.ShopFileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 高up
 * @description 针对表【order(订单表)】的数据库操作Service实现
 * @createDate 2022-04-18 14:26:40
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    public final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShopFileService shopFileService;

    public OrderServiceImpl(OrderMapper orderMapper,
                            OrderItemMapper orderItemMapper,
                            ShopFileService shopFileService) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.shopFileService = shopFileService;
    }

    @Override
    public ResultContext<IPage<OrderDTO>> getOrderList(OrderParam param) {
        Page<Order> page = new Page<>(param.getPageNum(), param.getPageSize());
        page = orderMapper.selectPage(page, Wrappers.<Order>lambdaQuery()
                .like(StrUtil.isNotBlank(param.getOrderSn()), Order::getOrderSn, param.getOrderSn())
                .eq(StrUtil.isNotBlank(param.getMemberUsername()), Order::getMemberUsername, param.getMemberUsername())
                .eq(null != param.getPayType(), Order::getPayType, param.getPayType())
                .eq(null != param.getStatus(), Order::getStatus, param.getStatus())
        );
        return ResultContext.buildSuccess("查询订单列表成功", page.convert(this::translation));
    }

    public OrderDTO translation(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtil.copyProperties(order, orderDTO);
        orderDTO.setPayTypeName(ENPayType.getLabelByValue(String.valueOf(order.getPayType())));
        orderDTO.setStatusName(ENStatus.getLabelByValue(String.valueOf(order.getStatus())));
        return orderDTO;
    }

    @Override
    public ResultContext<List<DropDownDTO>> getPayType() {
        List<DropDownDTO> dropDownDTOS = new ArrayList<>();
        //遍历支付方式枚举类
        for (ENPayType enPayType : ENPayType.values()) {
            DropDownDTO downDTO = new DropDownDTO();
            downDTO.setValue(enPayType.getValue());
            downDTO.setLabel(enPayType.getLabel());
            dropDownDTOS.add(downDTO);
        }
        return ResultContext.buildSuccess("成功获取支付方式下拉", dropDownDTOS);
    }

    @Override
    public ResultContext<List<DropDownDTO>> getStatus() {
        List<DropDownDTO> dropDownDTOS = new ArrayList<>();
        //遍历支付状态枚举类
        for (ENStatus enStatus : ENStatus.values()) {
            DropDownDTO downDTO = new DropDownDTO();
            downDTO.setValue(enStatus.getValue());
            downDTO.setLabel(enStatus.getLabel());
            dropDownDTOS.add(downDTO);
        }
        return ResultContext.buildSuccess("成功获取支付状态下拉", dropDownDTOS);
    }

    @Override
    public ResultContext<OrderDetail> getOrderDetail(Long id) {
        Order order = getBaseMapper().selectById(id);
        // 查询订单商品信息
        List<OrderItem> orderItems = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery()
                .eq(OrderItem::getOrderId, order.getId()));
        OrderDetail orderDetail = new OrderDetail();
        BeanUtil.copyProperties(order, orderDetail);

        orderDetail.setPayTypeName(ENPayType.getLabelByValue(String.valueOf(order.getPayType())));
        orderDetail.setStatusName(ENStatus.getLabelByValue(String.valueOf(order.getStatus())));

        List<OrderItemDTO> orderItemDTOList = orderItems.parallelStream().map(orderItem -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            BeanUtil.copyProperties(orderItem, orderItemDTO);

            orderItemDTO.setShopFileResp(shopFileService.getFileById(orderItem.getMainPicId()));
            return orderItemDTO;
        }).collect(Collectors.toList());

        orderDetail.setOrderItemList(orderItemDTOList);
        return ResultContext.buildSuccess(null, orderDetail);
    }
}




