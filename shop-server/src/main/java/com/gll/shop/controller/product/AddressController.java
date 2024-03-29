package com.gll.shop.controller.product;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.shop.common.beans.ResultContext;
import com.gll.shop.common.dropdown.DropDownDTO;
import com.gll.shop.entity.ReceiveAddress;
import com.gll.shop.entity.ReceiveAddressParam;
import com.gll.shop.service.address.ReceiveAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
@Slf4j
//下面的每一个方法都会检查是否登录
@SaCheckLogin
public class AddressController {
    public final ReceiveAddressService addressService;

    public AddressController(ReceiveAddressService addressService) {
        this.addressService = addressService;
    }
   @PostMapping("/getAddressList")
    public ResultContext<IPage<ReceiveAddress>> getAddressList(@RequestBody ReceiveAddressParam param)
    {
        return addressService.getAddressList(param);
    }
    @PostMapping("/getAddressById")
    public ResultContext<ReceiveAddress> getAddressById(@RequestBody JSONObject param)
    {
        Long id = param.getLong("id");
        return addressService.getAddressById(id);
    }
    @PostMapping("/inOrUpAddress")
    public ResultContext<Void> insertAndUpdateAddress(@RequestBody ReceiveAddress address)
    {
        return addressService.insertAndUpdateAddress(address);
    }
    @PostMapping("/deleteAddressById")
    public ResultContext<Void> deleteAddressById(@RequestBody List<Long> idList)
    {
        return addressService.deleteAddressById(idList);
    }

    @PostMapping("/getReceiveAddressDropDown")
    public ResultContext<List<DropDownDTO>> getReceiveAddressDropDown()
    {
        return addressService.getReceiveAddressDropDown();
    }


}
