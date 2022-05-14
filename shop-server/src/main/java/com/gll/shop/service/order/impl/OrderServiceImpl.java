package com.gll.shop.service.order.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.constant.Constant;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.common.enums.ENCacheKey;
import com.gll.shop.common.enums.ENPayType;
import com.gll.shop.common.enums.ENStatus;
import com.gll.shop.common.enums.ENUserRole;
import com.gll.shop.entity.*;
import com.gll.shop.mapper.*;
import com.gll.shop.service.SysRole.SysRoleService;
import com.gll.shop.service.SysRole.SysUserRoleService;
import com.gll.shop.service.order.OrderService;
import com.gll.shop.service.shopFile.ShopFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 高up
 * @description 针对表【order(订单表)】的数据库操作Service实现
 * @createDate 2022-04-18 14:26:40
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    public final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShopFileService shopFileService;
    private final ReceiveAddressMapper receiveAddressMapper;
    private final CartItemMapper cartItemMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductStockMapper productStockMapper;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    public OrderServiceImpl(OrderMapper orderMapper,
                            OrderItemMapper orderItemMapper,
                            ShopFileService shopFileService,
                            ReceiveAddressMapper receiveAddressMapper,
                            CartItemMapper cartItemMapper,
                            RedisTemplate<String, Object> redisTemplate,
                            ProductStockMapper productStockMapper,
                            SysUserRoleService sysUserRoleService,
                            SysRoleService sysRoleService) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.shopFileService = shopFileService;
        this.receiveAddressMapper = receiveAddressMapper;
        this.cartItemMapper = cartItemMapper;
        this.redisTemplate = redisTemplate;
        this.productStockMapper = productStockMapper;
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public ResultContext<IPage<OrderDTO>> getOrderList(OrderParam param) {
        Page<Order> page = new Page<>(param.getPageNum(), param.getPageSize());
        // 获取session
        SaSession session = StpUtil.getSession();
        SysUser userInfo = (SysUser) session.get(Constant.SESSION_USER_KEY);
        //得到用户id
        Integer id = userInfo.getId();
        //得到角色id
        String roleId = sysUserRoleService.getRoleIdByUserId(String.valueOf(id));
        //根据roleId得到 用户的身份
        String roleName = sysRoleService.getBaseMapper().selectById(roleId).getRoleName();
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = Wrappers.<Order>lambdaQuery()
                .like(StrUtil.isNotBlank(param.getOrderSn()), Order::getOrderSn, param.getOrderSn())
                .eq(StrUtil.isNotBlank(param.getMemberUsername()), Order::getMemberUsername, param.getMemberUsername())
                .eq(null != param.getPayType(), Order::getPayType, param.getPayType())
                .eq(null != param.getStatus(), Order::getStatus, param.getStatus());
        if (!StrUtil.equals(roleName, ENUserRole.ADMIN.getLabel())) {
            orderLambdaQueryWrapper.eq(Order::getMemberId, userInfo.getId());
        }
        page = orderMapper.selectPage(page, orderLambdaQueryWrapper);
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
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(ENCacheKey.ORDER.getValue() + id, TimeUnit.SECONDS);
        orderDetail.setOrderRemainingTime(expire);
        return ResultContext.buildSuccess(null, orderDetail);
    }

    @Override
    public ResultContext<String> closeOrder(List<Long> ids) {
        Date date = new Date();
        for (Long id : ids) {
            //将待付款状态订单的状态改为关闭状态
            LambdaUpdateWrapper<Order> lambdaUpdateWrapper = Wrappers.<Order>lambdaUpdate()
                    .set(Order::getStatus, ENStatus.CLOSED.getValue())
                    .set(Order::getModifyTime, date)
                    .eq(Order::getId, id)
                    .eq(Order::getStatus, ENStatus.WAITEPAY.getValue());
            boolean update = this.update(lambdaUpdateWrapper);
            if (update) {
                // 归还库存
                this.returnProductStock(id);
            }
        }
        return ResultContext.success("成功关闭订单");
    }

    @Override
    public ResultContext<String> deliveryOrder(List<Long> ids) {
        Date date = new Date();
        for (Long id : ids) {
            //将代发货状态的订单改为已发货状态
            LambdaUpdateWrapper<Order> lambdaUpdateWrapper = Wrappers.<Order>lambdaUpdate()
                    .set(Order::getStatus, ENStatus.DELIVERY.getValue())
                    .set(Order::getModifyTime, date)
                    .set(Order::getDeliveryTime, date)
                    .eq(Order::getId, id)
                    .eq(Order::getStatus, ENStatus.NOTDELIVERY.getValue());
            this.update(lambdaUpdateWrapper);
        }
        return ResultContext.success("成功发货订单");
    }

    @Override
    public ResultContext<String> confirmOrder(List<Long> ids) {
        Date date = new Date();
        for (Long id : ids) {
            //将代发货状态的订单改为已发货状态
            LambdaUpdateWrapper<Order> lambdaUpdateWrapper = Wrappers.<Order>lambdaUpdate()
                    .set(Order::getStatus, ENStatus.FINISHED.getValue())
                    .set(Order::getModifyTime, date)
                    .set(Order::getReceiveTime, date)
                    .eq(Order::getId, id)
                    .eq(Order::getStatus, ENStatus.DELIVERY.getValue());
            this.update(lambdaUpdateWrapper);
        }
        return ResultContext.success("成功确认收货");
    }

    @Override
    public ResultContext<String> deleteOrder(List<Long> ids) {
        getBaseMapper().deleteBatchIds(ids);
        return ResultContext.success("成功删除订单");
    }

    @Override
    public ResultContext<String> changeReceiveInfo(ReceiverInfo receiverInfo) {
        Order order = new Order();
        order.setId(receiverInfo.getOrderId());
        order.setReceiverName(receiverInfo.getReceiverName());
        order.setReceiverPhone(receiverInfo.getReceiverPhone());
        order.setReceiverPostCode(receiverInfo.getReceiverPostCode());
        order.setReceiverProvince(receiverInfo.getReceiverProvince());
        order.setReceiverCity(receiverInfo.getReceiverCity());
        order.setReceiverRegion(receiverInfo.getReceiverRegion());
        order.setReceiverDetailAddress(receiverInfo.getReceiverDetailAddress());
        getBaseMapper().updateById(order);
        return ResultContext.success("成功更新收货人信息");
    }

    @Override
    @Transactional
    public ResultContext<Long> submitOrder(OrderSubmitReq orderSubmitReq) {
        // 校验库存，并且对应库存减少
        for (CartItem cartItem : orderSubmitReq.getCartItemList()) {
            ProductStock productStock = productStockMapper.selectById(cartItem.getProductStockId());
            if (productStock.getStock() < cartItem.getQuantity()) {
                return ResultContext.businessFail("商品 " + cartItem.getProductName() + "库存不足!");
            } else {
                ProductStock productStock1 = new ProductStock();
                productStock1.setId(productStock.getId());
                productStock1.setStock(productStock.getStock() - cartItem.getQuantity());
                int updateById = productStockMapper.updateById(productStock1);
                if (updateById != 1) {
                    throw new RuntimeException("创建订单失败");
                }
            }
        }
        // 获取session
        SaSession session = StpUtil.getSession();
        SysUser userInfo = (SysUser) session.get(Constant.SESSION_USER_KEY);
        // 根据receiveAddressId获取地址详细信息
        ReceiveAddress receiveAddress = receiveAddressMapper.selectById(orderSubmitReq.getReceiveAddressId());
        Order order = new Order();
        order.setMemberId(userInfo.getId().longValue());
        order.setCouponId(null);
        // 生成订单编号
        String orderSn = String.valueOf(IdUtil.getSnowflake(1, 20).nextId());
        order.setOrderSn(orderSn);
        order.setCreateTime(new Date());
        order.setMemberUsername(userInfo.getUserName());
        order.setTotalAmount(orderSubmitReq.getPriceSum());
        order.setPayAmount(orderSubmitReq.getPriceSum());
        order.setFreightAmount(orderSubmitReq.getFreight());
        order.setPromotionAmount(null);
        order.setIntegrationAmount(null);
        order.setCouponAmount(null);
        order.setDiscountAmount(null);
        order.setPayType(ENPayType.NOTPAY.getIntValue());
        // 默认订单来源为PC订单
        order.setSourceType(0);
        // 待付款
        order.setStatus(ENStatus.WAITEPAY.getIntValue());
        // 正常订单
        order.setOrderType(0);
        // 默认顺丰物流
        order.setDeliveryCompany("顺丰");
        // 物流单号
        order.setDeliverySn(null);
        //
        order.setAutoConfirmDay(Constant.autoConfirmDay);
        order.setIntegration(null);
        order.setGrowth(null);
        order.setPromotionInfo(null);
        // 默认不开发票
        order.setBillType(0);
        order.setBillHeader(null);
        order.setBillContent(null);
        order.setBillReceiverPhone(null);
        order.setBillReceiverEmail(null);

        order.setReceiverName(receiveAddress.getName());
        order.setReceiverPhone(receiveAddress.getPhoneNumber());
        order.setReceiverPostCode(receiveAddress.getPostCode());
        order.setReceiverProvince(receiveAddress.getProvince());
        order.setReceiverCity(receiveAddress.getCity());
        order.setReceiverRegion(receiveAddress.getRegion());
        order.setReceiverDetailAddress(receiveAddress.getDetailAddress());
        // 订单备注
        order.setNote(null);
//        确认收货状态
        order.setConfirmStatus(null);
        // 订单删除状态（用于逻辑删除，暂留字段）
        order.setDeleteStatus(0);
        order.setUseIntegration(null);
        // 付款时间
        order.setPaymentTime(null);
        // 发货时间
        order.setDeliveryTime(null);
        // 确认收货时间
        order.setReceiveTime(null);
        // 评论时间
        order.setCommentTime(null);
        // 修改时间
        order.setModifyTime(new Date());

        int insert = orderMapper.insert(order);
        if (insert != 1) {
            throw new RuntimeException("订单创建失败");
        }

        // 构造 orderItem
        List<OrderItem> orderItemList = this.convert(order.getId(), orderSn, orderSubmitReq.getCartItemList());
        for (OrderItem orderItem : orderItemList) {
            int insert1 = orderItemMapper.insert(orderItem);
            if (insert1 != 1) {
                throw new RuntimeException("订单item创建失败");
            }
        }
        // 订单创建成功,删除购物车内容
        cartItemMapper.deleteBatchIds(orderSubmitReq.getCartItemList()
                .stream()
                .map(CartItem::getId)
                .collect(Collectors.toList()));

        // 设置订单的支付过期时间
        redisTemplate.opsForValue().set(ENCacheKey.ORDER.getValue() + order.getId(), order, Constant.orderExpireTimeSecond, TimeUnit.SECONDS);
        // 返回订单id
        return ResultContext.buildSuccess("订单提交成功", order.getId());
    }


    private List<OrderItem> convert(Long orderId, String orderSn, List<CartItem> cartItemList) {

        List<OrderItem> orderItemList = new ArrayList<>();
        cartItemList.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setOrderSn(orderSn);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductBrand(cartItem.getProductBrand());
            orderItem.setProductSn(cartItem.getProductSn());
            orderItem.setProductPrice(cartItem.getPrice());
            orderItem.setMainPicId(cartItem.getMainPicId());
            orderItem.setProductQuantity(cartItem.getQuantity());
            orderItem.setProductSkuId(cartItem.getProductStockId());
            orderItem.setProductSkuCode(cartItem.getProductCode());
            orderItem.setProductCategoryId(cartItem.getProductCategoryId());
            orderItem.setProductAttr(cartItem.getProductAttr());
            // 促销，优惠券相关
            orderItem.setPromotionName(null);
            orderItem.setPromotionAmount(null);
            orderItem.setCouponAmount(null);
            orderItem.setIntegrationAmount(null);
            orderItem.setRealAmount(null);
            orderItem.setGiftIntegration(null);
            orderItem.setGiftGrowth(null);

            orderItemList.add(orderItem);
        });
        return orderItemList;
    }

    @Override
    public ResultContext<String> payOrder(JSONObject param) {
        Long id = param.getLong("id");
        Integer payType = param.getInt("payType");
        Order order = new Order();
        order.setStatus(ENStatus.NOTDELIVERY.getIntValue());
        order.setPaymentTime(new Date());
        order.setPayType(payType);
        boolean update = this.update(order, Wrappers.<Order>lambdaUpdate()
                .eq(Order::getId, id)
                .eq(Order::getStatus, ENStatus.WAITEPAY.getIntValue()));
        if (update) {
            redisTemplate.delete(ENCacheKey.ORDER.getValue() + id);
            return ResultContext.success("支付成功！");
        } else {
            return ResultContext.success("支付失败！");

        }
    }

    @Override
    public void returnProductStock(Long orderId) {
        // 查询orderItem
        List<OrderItem> orderItemList = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery()
                .eq(OrderItem::getOrderId, orderId));

        for (OrderItem orderItem : orderItemList) {
            ProductStock productStock = productStockMapper.selectById(orderItem.getProductSkuId());

            ProductStock productStock1 = new ProductStock();
            productStock1.setId(productStock.getId());
            productStock1.setStock(productStock.getStock() + orderItem.getProductQuantity());
            int updateById = productStockMapper.updateById(productStock1);
            if (updateById != 1) {
                log.error("订单库存归还失败");
            } else {
                log.info("订单库存归还成功");
            }
        }
    }
}




