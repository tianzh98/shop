package com.gll.shop.service.user;

import com.gll.shop.entity.SysUserPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author gaoll
 */
public interface SysUserService extends IService<SysUserPO> {
    SysUserPO getUser(String accountOrEmailOrPhone);

}
