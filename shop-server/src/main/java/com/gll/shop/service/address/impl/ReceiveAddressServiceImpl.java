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

import java.util.List;

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
    public final ReceiveAddressMapper receiveAddressMapper;

    public ReceiveAddressServiceImpl(SysRoleService sysRoleService, SysUserRoleService sysUserRoleService, ReceiveAddressMapper receiveAddressMapper) {
        this.sysRoleService = sysRoleService;
        this.sysUserRoleService = sysUserRoleService;
        this.receiveAddressMapper = receiveAddressMapper;
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

    @Override
    public ResultContext<ReceiveAddress> getAddressById(Long id) {
        ReceiveAddress receiveAddress = receiveAddressMapper.selectById(id);
        return ResultContext.buildSuccess("查询地址成功",receiveAddress);
    }

    @Override
    public ResultContext<Void> insertAndUpdateAddress(ReceiveAddress address) {
        Long id = address.getId();
        int result = -1;
        if(null == id)
        {
            //插入地址
           result = receiveAddressMapper.insert(address);
        }else
        {
            //更新地址
            result = receiveAddressMapper.updateById(address);
        }
        if(result <= 0)
        {
            throw  new RuntimeException("插入或者更新地址错误");
        }
        return ResultContext.buildSuccess("插入或者更新地址成功",null);
    }

    @Override
    public ResultContext<Void> deleteAddressById(List<Long> idList) {
        int result = -1;
        result = receiveAddressMapper.deleteBatchIds(idList);
        if(result <= 0)
        {
            throw  new RuntimeException("删除地址错误");
        }
        return ResultContext.buildSuccess("删除地址成功",null);
    }
}




