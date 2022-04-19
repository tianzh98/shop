package com.gll.shop.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ProductAttributeParam implements Serializable {
    private static final long serialVersionUID = -169404289235496510L;
    //商品类型的id
   private Integer id;
    //根据这个参数判断 是商品属性查询还是商品参数查询 0->商品属性查询  1->商品参数查询
   private Integer type;
}
