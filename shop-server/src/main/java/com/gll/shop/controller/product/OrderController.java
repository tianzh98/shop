package com.gll.shop.controller.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.OrderDTO;
import com.gll.shop.entity.OrderDetail;
import com.gll.shop.entity.OrderParam;
import com.gll.shop.entity.ReceiverInfo;
import com.gll.shop.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
//下面的每一个方法都会检查是否登录
@SaCheckLogin
public class OrderController {
    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/getOrderList")
    public ResultContext<IPage<OrderDTO>> getOrderLists(@RequestBody OrderParam param) {
        return orderService.getOrderList(param);
    }

    @PostMapping("/getPayType")
    public ResultContext<List<DropDownDTO>> getPayTypes() {
        return orderService.getPayType();
    }

    @PostMapping("/getStatus")
    public ResultContext<List<DropDownDTO>> getStatuses() {
        return orderService.getStatus();
    }

    @PostMapping("/closeOrder")
    public ResultContext<String> closeOrder(@RequestBody List<Long> ids) {
        return orderService.closeOrder(ids);
    }

    @PostMapping("/deliveryOrder")
    public ResultContext<String> deliveryOrder(@RequestBody List<Long> ids) {
        return orderService.deliveryOrder(ids);
    }


    @PostMapping("/getOrderDetail")
    public ResultContext<OrderDetail> getOrderDetail(@RequestBody JSONObject param) {
        Long id = param.getLong("id");
        return orderService.getOrderDetail(id);
    }

    @PostMapping("/deleteOrder")
    public ResultContext<String> getOrderDetail(@RequestBody List<Long> ids) {
        return orderService.deleteOrder(ids);
    }
    @PostMapping("/changeReceiveInfo")
    public ResultContext<String> changeReceiveInfoOrder(@RequestBody ReceiverInfo receiverInfo) {
        return orderService.changeReceiveInfo(receiverInfo);
    }

}
