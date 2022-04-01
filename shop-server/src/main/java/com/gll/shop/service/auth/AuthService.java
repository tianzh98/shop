package com.gll.shop.service.auth;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.constant.Constant;
import com.gll.shop.common.enums.ENResourcesType;
import com.gll.shop.common.enums.ENUserStatus;
import com.gll.shop.entity.SysResourcePO;
import com.gll.shop.entity.SysRoleResPO;
import com.gll.shop.entity.SysUserPO;
import com.gll.shop.entity.SysUserRolePO;
import com.gll.shop.entity.rest.LoginResult;
import com.gll.shop.mapper.SysResourceMapper;
import com.gll.shop.mapper.SysRoleMapper;
import com.gll.shop.mapper.SysRoleResMapper;
import com.gll.shop.mapper.SysUserRoleMapper;
import com.gll.shop.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaoll
 */
@Service
public class AuthService {

    private final SysRoleMapper sysRoleMapper;
    private final SysResourceMapper sysResourceMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserService sysUserService;

    private final SysRoleResMapper sysRoleResMapper;

    @Autowired
    public AuthService(SysRoleMapper sysRoleMapper,
                       SysResourceMapper sysResourceMapper,
                       SysUserRoleMapper sysUserRoleMapper, SysUserService sysUserService, SysRoleResMapper sysRoleResMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysResourceMapper = sysResourceMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysUserService = sysUserService;
        this.sysRoleResMapper = sysRoleResMapper;
    }

    public ResultContext<LoginResult> login(String accountOrEmailOrPhone, String password) {

        // ----------------可以不调用------------------------
        SysUserPO userInfo = sysUserService.getUser(accountOrEmailOrPhone);

        if (userInfo == null) {
            return ResultContext.businessFail("用户不存在");
        }
        if (ENUserStatus.DISABLE.value.equals(userInfo.getStatus())) {
            return ResultContext.businessFail("用户账号被锁定");
        }
        // 比对密码
        if (!StrUtil.equals(password, userInfo.getPassword())) {
            return ResultContext.businessFail("密码错误");
        }

        // sa-token 登录
        StpUtil.login(userInfo.getId());
        // 获取token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 获取session
        SaSession session = StpUtil.getSession();
        // 设置用户信息
        session.set(Constant.SESSION_USER_KEY, userInfo);
        return ResultContext.buildSuccess("登录成功!", new LoginResult(tokenInfo.getTokenValue(), userInfo));
    }


    public ResultContext<JSONArray> queryUserMenu() {
        JSONArray jsonArray = new JSONArray();

        List<String> resIds = getLoginUserResIds();
        if (CollectionUtil.isEmpty(resIds)) {
            return ResultContext.buildSuccess("", jsonArray);
        }
        // 去资源表里 把菜单信息拿到
        // 先拿目录
        List<SysResourcePO> sysResourcePoList = sysResourceMapper.selectList(Wrappers.<SysResourcePO>lambdaQuery()
                .in(SysResourcePO::getId, resIds)
                .eq(SysResourcePO::getType, ENResourcesType.CATALOGUE.value)
                .orderByAsc(SysResourcePO::getSerialNo));

        sysResourcePoList.forEach(sysResourcePo -> {
            // 获取目录 以及其子菜单
            JSONObject content = getFromSysResource(sysResourcePo);

            jsonArray.add(content);
        });

        return ResultContext.buildSuccess("", jsonArray);
    }

    List<String> getLoginUserResIds() {
        // 获取userId
        int userId = StpUtil.getLoginIdAsInt();
        // 获取该用户的角色
        List<String> roleIds = sysUserRoleMapper.selectList(Wrappers.<SysUserRolePO>lambdaQuery()
                .select(SysUserRolePO::getRoleId)
                .eq(SysUserRolePO::getUserId, userId))
                .stream().map(SysUserRolePO::getRoleId).collect(Collectors.toList());
        // 根据角色-资源关系表把每个角色的资源权限都查到
        List<String> resIds = sysRoleResMapper.selectList(Wrappers.<SysRoleResPO>lambdaQuery()
                .in(SysRoleResPO::getRoleId, roleIds))
                .stream().distinct().map(SysRoleResPO::getResId).collect(Collectors.toList());
        return resIds;
    }

    private JSONObject getFromSysResource(SysResourcePO sysresourcepo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("icon", sysresourcepo.getIcon());
        jsonObject.set("id", sysresourcepo.getId());
        jsonObject.set("serialNo", sysresourcepo.getSerialNo());
        jsonObject.set("title", sysresourcepo.getTitle());
        jsonObject.set("url", sysresourcepo.getUrl());

        JSONArray menuArray = new JSONArray();
        // 查看底下有无菜单
        List<SysResourcePO> menus = sysResourceMapper.selectList(Wrappers.<SysResourcePO>lambdaQuery()
                .eq(SysResourcePO::getPid, sysresourcepo.getId())
                .eq(SysResourcePO::getType, ENResourcesType.MENU.value)
                .orderByAsc(SysResourcePO::getSerialNo));

        menus.forEach(menuRes -> {
            menuArray.add(getFromSysResource(menuRes));
        });
        jsonObject.set("childrenNode", menuArray);
        return jsonObject;
    }

    public ResultContext<JSONArray> queryUserPermission() {
        // 获取资源ID
        List<String> resIds = getLoginUserResIds();

        // 拿到所有按钮
        List<SysResourcePO> sysResourcePoList = sysResourceMapper.selectList(Wrappers.<SysResourcePO>lambdaQuery()
                .in(SysResourcePO::getId, resIds)
                .eq(SysResourcePO::getType, ENResourcesType.BUTTON.value)
                .orderByAsc(SysResourcePO::getSerialNo));

        // 找到它们父亲的menuCode, 并分类
        // 按钮的父亲 一定要是 菜单  不能是目录
        Map<String, List<SysResourcePO>> menuCodeResMap = sysResourcePoList.stream().collect(Collectors.groupingBy(sysResourcePO -> {
            SysResourcePO sysResourcePO1 = sysResourceMapper.selectById(sysResourcePO.getPid());
            return sysResourcePO1.getMenuCode();
        }));

        JSONArray jsonArray = new JSONArray();

        menuCodeResMap.forEach((menuCode, btnRes) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("menuCode", menuCode);
            JSONArray authList = new JSONArray();
            btnRes.forEach(btn -> {
                authList.add(btn.getPermission());
            });

            jsonObject.set("permissionList", authList);

            jsonArray.add(jsonObject);
        });


        return ResultContext.buildSuccess(null, jsonArray);
    }
}