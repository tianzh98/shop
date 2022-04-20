package com.gll.shop.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.entity.SysUser;
import com.gll.shop.mapper.SysUserMapper;
import com.gll.shop.service.user.SysUserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    @Override
    public SysUser getUser(String accountOrEmailOrPhone) {
        return baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getAccount, accountOrEmailOrPhone)
                .or()
                .eq(SysUser::getEmail, accountOrEmailOrPhone)
                .or()
                .eq(SysUser::getPhone, accountOrEmailOrPhone));
    }
}




