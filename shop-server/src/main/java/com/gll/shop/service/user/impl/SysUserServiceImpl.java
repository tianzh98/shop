package com.gll.shop.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.entity.SysUserPO;
import com.gll.shop.mapper.SysUserMapper;
import com.gll.shop.service.user.SysUserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPO>
        implements SysUserService {

    @Override
    public SysUserPO getUser(String accountOrEmailOrPhone) {
        return baseMapper.selectOne(Wrappers.<SysUserPO>lambdaQuery()
                .eq(SysUserPO::getAccount, accountOrEmailOrPhone)
                .or()
                .eq(SysUserPO::getEmail, accountOrEmailOrPhone)
                .or()
                .eq(SysUserPO::getPhone, accountOrEmailOrPhone));
    }
}




