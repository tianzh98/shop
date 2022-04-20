package com.gll.shop.service.address;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.entity.ReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.entity.RessiveAddressParam;

/**
* @author 高up
* @description 针对表【receive_address(收货地址表)】的数据库操作Service
* @createDate 2022-04-20 18:28:02
*/
public interface ReceiveAddressService extends IService<ReceiveAddress> {

    ResultContext<IPage<ReceiveAddress>> getAddressList(RessiveAddressParam param);
}
