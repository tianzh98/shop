package com.gll.shop.config.redis;

import cn.hutool.core.util.StrUtil;
import com.gll.shop.common.enums.ENCacheKey;
import com.gll.shop.common.enums.ENStatus;
import com.gll.shop.entity.Order;
import com.gll.shop.mapper.ProductStockMapper;
import com.gll.shop.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author gaoll
 * @date 2022年05月03日
 */

@Component
@Slf4j
public class OrderExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderService orderService;


    public OrderExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 开启redis key 过期监听功能，需要修改redis的配置，
     * win环境：打开redis.windows.conf 文件
     * 修改配置 notify-keyspace-events ""  为 notify-keyspace-events Ex   然后重启redis
     *
     * @param message message
     * @param pattern pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 已过期的key名称
        final String expiredKey = message.toString();
        if (StrUtil.startWith(expiredKey, ENCacheKey.ORDER.getValue())) {
            String[] split = expiredKey.split(StrUtil.COLON);
            Long orderId = Long.parseLong(split[1]);
            // 查询订单状态
            Order byId = orderService.getById(orderId);
            if (ENStatus.WAITEPAY.getIntValue().equals(byId.getStatus())) {
                // 订单已过期，更新订单状态
                Order order = new Order();
                order.setId(orderId);
                order.setStatus(ENStatus.CLOSED.getIntValue());
                orderService.updateById(order);
                log.info("订单号[{}],已过期，系统已自动关闭订单", orderId);
                // 库存归还
                orderService.returnProductStock(orderId);
            }
            // 不是待付款的订单就不用管
        }
    }
}
