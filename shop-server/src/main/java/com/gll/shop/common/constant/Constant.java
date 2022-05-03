package com.gll.shop.common.constant;


/**
 * @author gaoll
 */
public interface Constant {

    String SESSION_USER_KEY = "UserInfo";
    /**
     * 存储用户权限
     */
    String REDIS_CAPTCHA_KEY = "user:captcha:";

    String COMMON_PARENT_ID = "0";
    String ROUTER_LAYOUT = "Layout";
    String ROUTER_PARENT_VIEW = "ParentView";
    String ROUTER_NO_REDIRECT = "noRedirect";

    /**
     * 订单过期时间，单位秒
     */
    Integer orderExpireTimeSecond = 3600;
    /**
     * 订单15天后自动确认
     */
    Integer autoConfirmDay = 15;
}
