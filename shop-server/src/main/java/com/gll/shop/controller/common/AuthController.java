package com.gll.shop.controller.common;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONArray;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.auth.LoginResult;
import com.gll.shop.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gaoll
 * @date 2022年03月27日
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    /**
     * 注意接受参数必须是 json对象。 而不能单用String类型接受。 因为前端是传的json字符串
     *
     * @param loginInfo 账号密码
     * @return LoginResult
     */
    @PostMapping("/logIn")
    public ResultContext<LoginResult> logIn(@RequestBody Map<String, String> loginInfo) {
        Assert.notEmpty(loginInfo);

        String accountOrEmailOrPhone = loginInfo.get("accountOrEmailOrPhone");
        String password = loginInfo.get("password");

        Assert.notBlank(accountOrEmailOrPhone);
        Assert.notBlank(password);
        return authService.login(accountOrEmailOrPhone, password);
    }

    /**
     * 退出登录
     *
     * @return success
     */
    @PostMapping("/logOut")
    public ResultContext<String> logOut() {
        StpUtil.logout();
        return ResultContext.success("退出登录成功！");
    }

    @SaCheckLogin
    @PostMapping("/queryUserMenu")
    public ResultContext<JSONArray> queryUserMenu() {
        return authService.queryUserMenu();
    }

    @SaCheckLogin
    @PostMapping("/queryUserPermission")
    public ResultContext<JSONArray> queryUserPermission() {
        return authService.queryUserPermission();
    }

}
