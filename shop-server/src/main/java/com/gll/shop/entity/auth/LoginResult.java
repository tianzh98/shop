package com.gll.shop.entity.auth;

import com.gll.shop.entity.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gaoll
 * @date 2022年03月27日
 */
@Data
@AllArgsConstructor
public class LoginResult implements Serializable {
    private static final long serialVersionUID = 3694874991377933216L;
    private String token;
    private SysUserPO userInfo;

}
