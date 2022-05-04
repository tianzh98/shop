package com.gll.shop.service.address.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.constant.Constant;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.common.enums.ENUserRole;
import com.gll.shop.entity.ReceiveAddress;
import com.gll.shop.entity.ReceiveAddressParam;
import com.gll.shop.entity.SysUser;
import com.gll.shop.mapper.ReceiveAddressMapper;
import com.gll.shop.service.SysRole.SysRoleService;
import com.gll.shop.service.SysRole.SysUserRoleService;
import com.gll.shop.service.address.ReceiveAddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 高up
 * @description 针对表【receive_address(收货地址表)】的数据库操作Service实现
 * @createDate 2022-04-20 18:28:02
 */
@Service
public class ReceiveAddressServiceImpl extends ServiceImpl<ReceiveAddressMapper, ReceiveAddress>
        implements ReceiveAddressService {
    public final SysRoleService sysRoleService;
    public final SysUserRoleService sysUserRoleService;
    public final ReceiveAddressMapper receiveAddressMapper;

    public ReceiveAddressServiceImpl(SysRoleService sysRoleService, SysUserRoleService sysUserRoleService, ReceiveAddressMapper receiveAddressMapper) {
        this.sysRoleService = sysRoleService;
        this.sysUserRoleService = sysUserRoleService;
        this.receiveAddressMapper = receiveAddressMapper;
    }

    @Override
    public ResultContext<IPage<ReceiveAddress>> getAddressList(ReceiveAddressParam param) {
        // 获取session
        SaSession session = StpUtil.getSession();
        SysUser userInfo = (SysUser) session.get(Constant.SESSION_USER_KEY);
        //得到用户id
        Integer id = userInfo.getId();
        //得到角色id
        String roleId = sysUserRoleService.getRoleIdByUserId(String.valueOf(id));
        //根据roleId得到 用户的身份
        String roleName = sysRoleService.getBaseMapper().selectById(roleId).getRoleName();

        IPage<ReceiveAddress> page = new Page<>(param.getPageNum(), param.getPageSize());
        if (roleName.equals(ENUserRole.ADMIN.getLabel())) {
            page = getBaseMapper().selectPage(page, Wrappers.<ReceiveAddress>lambdaQuery()
                    .like(StrUtil.isNotBlank(param.getName()), ReceiveAddress::getName, param.getName())
                    .like(StrUtil.isNotBlank(param.getDetailAddress()), ReceiveAddress::getDetailAddress, param.getDetailAddress())
            );
        } else {
            page = getBaseMapper().selectPage(page, Wrappers.<ReceiveAddress>lambdaQuery()
                    .eq(ReceiveAddress::getUserId, id)
                    .like(StrUtil.isNotBlank(param.getDetailAddress()), ReceiveAddress::getDetailAddress, param.getDetailAddress())
            );
        }
        return ResultContext.buildSuccess("查询地址成功", page);
    }

    @Override
    public ResultContext<ReceiveAddress> getAddressById(Long id) {
        ReceiveAddress receiveAddress = receiveAddressMapper.selectById(id);
        return ResultContext.buildSuccess("查询地址成功", receiveAddress);
    }

    @Override
    public ResultContext<Void> insertAndUpdateAddress(ReceiveAddress address) {
        Long id = address.getId();
        int result = -1;
        if (null == id) {
            //插入地址
            // 新增地址需要指定userId
            Long userId = StpUtil.getLoginIdAsLong();
            address.setUserId(userId);
            result = receiveAddressMapper.insert(address);
        } else {
            //更新地址
            result = receiveAddressMapper.updateById(address);
        }
        if (result <= 0) {
            throw new RuntimeException("插入或者更新地址错误");
        }
        return ResultContext.buildSuccess("插入或者更新地址成功", null);
    }

    @Override
    public ResultContext<Void> deleteAddressById(List<Long> idList) {
        int result = -1;
        result = receiveAddressMapper.deleteBatchIds(idList);
        if (result <= 0) {
            throw new RuntimeException("删除地址错误");
        }
        return ResultContext.buildSuccess("删除地址成功", null);
    }

    @Override
    public ResultContext<List<DropDownDTO>> getReceiveAddressDropDown() {
        long userId = StpUtil.getLoginIdAsLong();
        List<ReceiveAddress> receiveAddressList = getBaseMapper().selectList(Wrappers.<ReceiveAddress>lambdaQuery()
                .eq(ReceiveAddress::getUserId, userId)
                .orderByDesc(ReceiveAddress::getDefaultStatus));
        List<DropDownDTO> result = new ArrayList<>(receiveAddressList.size());
        receiveAddressList.forEach(receiveAddress -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("收货人：" + receiveAddress.getName());
            stringBuilder.append(StrUtil.COMMA);
            stringBuilder.append("电话：" + receiveAddress.getPhoneNumber());
            stringBuilder.append(receiveAddress.getProvince());
            stringBuilder.append(StrUtil.COMMA);
            stringBuilder.append(receiveAddress.getCity());
            stringBuilder.append(StrUtil.COMMA);
            stringBuilder.append(receiveAddress.getRegion());
            stringBuilder.append(StrUtil.COMMA);
            stringBuilder.append(receiveAddress.getDetailAddress());

            DropDownDTO dropDownDTO = new DropDownDTO();
            dropDownDTO.setLabel(stringBuilder.toString());
            dropDownDTO.setValue(String.valueOf(receiveAddress.getId()));
            result.add(dropDownDTO);
        });
        return ResultContext.buildSuccess(null, result);
    }
}




