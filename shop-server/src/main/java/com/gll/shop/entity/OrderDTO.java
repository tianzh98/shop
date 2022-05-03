package com.gll.shop.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO extends Order {

    private static final long serialVersionUID = -2011995460310526996L;
    private String payTypeName;
    private String statusName;
}
