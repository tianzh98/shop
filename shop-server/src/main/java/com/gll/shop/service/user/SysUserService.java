package com.gll.shop.service.user;

import com.gll.shop.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author gaoll
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getUser(String accountOrEmailOrPhone);

}
