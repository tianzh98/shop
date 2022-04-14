package com.gll.shop.controller.common;

/**
 * @author gaoll
 * @date 2022年03月31日
 */

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.gll.shop.common.beans.ResultContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalException {

    /**
     * 全局异常拦截（拦截项目中的所有异常）
     *
     * @param e
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = {NotLoginException.class, NotRoleException.class, NotPermissionException.class, DisableLoginException.class})
    public ResultContext<String> handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.error("controller层出现异常", e);

        // 不同异常返回不同状态码
        ResultContext<String> resultContext;
        if (e instanceof NotLoginException) {
            // 如果是未登录异常
            NotLoginException ee = (NotLoginException) e;
            resultContext = ResultContext.notLogin("请登录！");
        } else if (e instanceof NotRoleException) {
            // 如果是角色异常
            NotRoleException ee = (NotRoleException) e;
            resultContext = ResultContext.businessFail("权限不足！");
        } else if (e instanceof NotPermissionException) {
            // 如果是权限异常
            NotPermissionException ee = (NotPermissionException) e;
            resultContext = ResultContext.businessFail("权限不足！");
        } else if (e instanceof DisableLoginException) {
            // 如果是被封禁异常
            DisableLoginException ee = (DisableLoginException) e;
            resultContext = ResultContext.businessFail("账号被封禁：" + ee.getDisableTime() + "秒后解封！");
        } else {    // 普通异常, 输出：500 + 异常信息
            resultContext = ResultContext.businessFail("程序出现异常，请联系管理员！");
        }

        // 返回给前端
        return resultContext;
    }

/*    @SaCheckLogin
    private void my(){
        System.out.println("你的逻辑");
    }
    // 代理模式，动态代理,面向切面
    private void around(){
        // 切面代码
        try {
            this.my();  // SaCheckLogin
        } catch (Exception e) {
            handlerException(e);
    }

        // 切面代码
    }
    private void before(){
        // 切面代码
        this.my();
    }
    private void after(){
        this.my();
        // 切面代码
    }*/
}