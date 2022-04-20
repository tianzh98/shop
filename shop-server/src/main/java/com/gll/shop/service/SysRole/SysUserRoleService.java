package com.gll.shop.service.SysRole;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.entity.SysUserRole;

/**
* @author 高up
* @description 针对表【sys_user_role(用户角色关联表)】的数据库操作Service
* @createDate 2022-04-20 20:46:59
*/
public interface SysUserRoleService extends IService<SysUserRole> {
  String getRoleIdByUserId(String userId);
}
