package com.gll.shop.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.entity.OrderParam;

import java.util.List;

/**
* @author 高up
* @description 针对表【order(订单表)】的数据库操作Service
* @createDate 2022-04-18 14:26:40
*/
public interface OrderService extends IService<Order> {

    ResultContext<IPage<Order>> getOrderList(OrderParam param);

    ResultContext<List<DropDownDTO>> getPayType();

    ResultContext<List<DropDownDTO>> getStatus();
}
