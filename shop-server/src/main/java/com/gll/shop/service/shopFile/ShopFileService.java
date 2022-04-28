package com.gll.shop.service.shopFile;

import com.gll.shop.entity.ShopFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.shop.entity.common.ShopFileResp;

/**
 *
 */
public interface ShopFileService extends IService<ShopFile> {

    ShopFileResp convertImg(ShopFile shopFile);

    ShopFileResp getFileById(Long id);
}
