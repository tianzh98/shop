package com.gll.shop.service.address.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ReceiveAddress;
import com.gll.shop.entity.RessiveAddressParam;
import com.gll.shop.entity.SysUser;
import com.gll.shop.mapper.ReceiveAddressMapper;
import com.gll.shop.service.SysRole.SysRoleService;
import com.gll.shop.service.SysRole.SysUserRoleService;
import com.gll.shop.service.address.ReceiveAddressService;
import org.springframework.stereotype.Service;

/**
* @author 高up
* @description 针对表【receive_address(收货地址表)】的数据库操作Service实现
* @createDate 2022-04-20 18:28:02
*/
@Service
public class ReceiveAddressServiceImpl extends ServiceImpl<ReceiveAddressMapper, ReceiveAddress>
    implements ReceiveAddressService{
    public final SysRoleService sysRoleService;
    public final SysUserRoleService sysUserRoleService;

    public ReceiveAddressServiceImpl(SysRoleService sysRoleService, SysUserRoleService sysUserRoleService) {
        this.sysRoleService = sysRoleService;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public ResultContext<IPage<ReceiveAddress>> getAddressList(RessiveAddressParam param) {
        // 获取session
        SaSession session = StpUtil.getSession();
        SysUser userInfo = (SysUser) session.get("UserInfo");
        //得到用户id
        Integer id = userInfo.getId();
        //得到角色id
        String roleId = sysUserRoleService.getRoleIdByUserId(String.valueOf(id));
        //根据roleId得到 用户的身份
        String roleName = sysRoleService.getBaseMapper().selectById(roleId).getRoleName();

        IPage<ReceiveAddress> page = new Page<>(param.getPageNum(),param.getPageSize());
        if(roleName.equals("管理员"))
        {
           page = getBaseMapper().selectPage(page, Wrappers.<ReceiveAddress>lambdaQuery()
                   .eq(StrUtil.isNotBlank(param.getName()),ReceiveAddress::getName,param.getName())
           );
           return ResultContext.buildSuccess("查询地址成功",page);
        }else
        {
            page = getBaseMapper().selectPage(page, Wrappers.<ReceiveAddress>lambdaQuery()
                    .eq(ReceiveAddress::getUserId,id)
                    .eq(StrUtil.isNotBlank(param.getName()),ReceiveAddress::getName,param.getName()
            ));
            return ResultContext.buildSuccess("查询地址成功",page);
        }
    }
}




