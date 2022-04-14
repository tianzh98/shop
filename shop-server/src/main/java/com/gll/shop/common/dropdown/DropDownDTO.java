package com.gll.shop.common.dropdown;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DropDownDTO implements Serializable {
    private static final long serialVersionUID = 5681115123294866822L;

    private String label;
    private String value;

    private List<DropDownDTO> children;
}
