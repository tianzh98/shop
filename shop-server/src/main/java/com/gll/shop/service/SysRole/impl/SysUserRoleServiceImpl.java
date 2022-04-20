package com.gll.shop.service.SysRole.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.entity.SysUserRole;
import com.gll.shop.mapper.SysUserRoleMapper;
import com.gll.shop.service.SysRole.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
* @author 高up
* @description 针对表【sys_user_role(用户角色关联表)】的数据库操作Service实现
* @createDate 2022-04-20 20:46:59
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

    @Override
    public String getRoleIdByUserId(String userId) {
        return this.getBaseMapper().selectById(userId).getRoleId();
    }
}




