package com.gll.shop.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiverInfo implements Serializable {
    private static final long serialVersionUID = 4859639215449438900L;
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
}
