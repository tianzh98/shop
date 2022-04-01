//package com.gll.shop.satoken;
//
//import cn.dev33.satoken.stp.StpInterface;
//import com.gll.shop.service.auth.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
///**
// * @author gaoll
// */
//@Component
//public class StpInterfaceImpl implements StpInterface {
//
//    @Autowired
//    private AuthService authService;
//
//    /**
//     * 返回账号拥有的权限码
//     *
//     * @param loginId
//     * @param loginKey
//     * @return
//     */
//    @Override
//    public List<String> getPermissionList(Object loginId, String loginKey) {
//        return authService.getPermissionListByUserId((String) loginId);
//    }
//
//    /**
//     * 返回账号拥有的角色标识
//     *
//     * @param loginId
//     * @param loginKey
//     * @return
//     */
//    @Override
//    public List<String> getRoleList(Object loginId, String loginKey) {
//        return authService.getAuthRoleListByUserId((String) loginId)
//                .stream()
//                .map(Role::getRoleKey)
//                .collect(Collectors.toList());
//    }
//
//
//}
