package com.gll.shop.service.shopFile.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.shop.common.enums.ENCacheKey;
import com.gll.shop.entity.ShopFile;
import com.gll.shop.entity.common.ShopFileResp;
import com.gll.shop.service.shopFile.ShopFileService;
import com.gll.shop.mapper.ShopFileMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
@Service
public class ShopFileServiceImpl extends ServiceImpl<ShopFileMapper, ShopFile>
        implements ShopFileService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ShopFileServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * shopFile 转换成ShopFileResp，进行图片缩放
     *
     * @param shopFile shopFile
     * @return
     */
    @Override
    public ShopFileResp convertImg(ShopFile shopFile) {
        Assert.notNull(shopFile);

        ShopFileResp resp = new ShopFileResp();
        // 必须先把shopFile.getFileBase64()转成String再调用ImgUtil.toImage 方法，否则报错
        String oldString = new String(shopFile.getFileBase64());
        String base64String;
        String imageType;

        Boolean imageScale = (Boolean) redisTemplate.opsForValue().get(ENCacheKey.IMAGE_SCALE.getValue());


        if (null == imageScale) {
            redisTemplate.opsForValue().set(ENCacheKey.IMAGE_SCALE.getValue(), ENCacheKey.IMAGE_SCALE.getDefaultValue());
            imageScale = (Boolean) ENCacheKey.IMAGE_SCALE.getDefaultValue();
        }

        if (imageScale) {
            Integer width = (Integer) redisTemplate.opsForValue().get(ENCacheKey.IMAGE_WIDTH.getValue());
            Integer height = (Integer) redisTemplate.opsForValue().get(ENCacheKey.IMAGE_HEIGHT.getValue());
            String imageTypeCache = (String) redisTemplate.opsForValue().get(ENCacheKey.IMAGE_TYPE.getValue());

            if (null == width) {
                redisTemplate.opsForValue().set(ENCacheKey.IMAGE_WIDTH.getValue(), ENCacheKey.IMAGE_WIDTH.getDefaultValue());
                width = (Integer) ENCacheKey.IMAGE_WIDTH.getDefaultValue();
            }
            if (null == height) {
                redisTemplate.opsForValue().set(ENCacheKey.IMAGE_HEIGHT.getValue(), ENCacheKey.IMAGE_HEIGHT.getDefaultValue());
                height = (Integer) ENCacheKey.IMAGE_HEIGHT.getDefaultValue();
            }
            if (null == imageTypeCache) {
                redisTemplate.opsForValue().set(ENCacheKey.IMAGE_TYPE.getValue(), ENCacheKey.IMAGE_TYPE.getDefaultValue());
                imageTypeCache = (String) ENCacheKey.IMAGE_TYPE.getDefaultValue();
            }


            // 进行图片缩放，返回给前端的图片都是固定尺寸
            BufferedImage bufferedImage = ImgUtil.toImage(oldString);

            Image scaleImg = ImgUtil.scale(bufferedImage, width, height);

            base64String = ImgUtil.toBase64(scaleImg, imageTypeCache);
            imageType = imageTypeCache;
        } else {
            base64String = oldString;
            imageType = shopFile.getFileType();
        }

        resp.setBase64String(base64String);
        resp.setId(shopFile.getId());
        resp.setFileType(imageType);
        return resp;

    }


    @Override
    public ShopFileResp getFileById(Long id) {
        // map类型的redis对象 的 key
        String hashKey = String.valueOf(id);
        ShopFileResp shopFileResp;
        // 先从缓存获取. hash类型，key必须是string
        shopFileResp = (ShopFileResp) redisTemplate.opsForHash().get(ENCacheKey.ShopFileResp.getValue(), hashKey);
        if (shopFileResp == null) {
            ShopFile shopFile = getBaseMapper().selectById(id);
            if (shopFile == null) {
                return null;
            }
            shopFileResp = this.convertImg(shopFile);
            redisTemplate.opsForHash().put(ENCacheKey.ShopFileResp.getValue(), hashKey, shopFileResp);
        }
        return shopFileResp;
    }
}




