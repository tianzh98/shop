package com.gll.shop.entity.common;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class BaseQueryParams implements Serializable {
    private static final long serialVersionUID = 2227634848787001565L;
    private Integer pageNum = 1;
    private Integer pageSize = 200;
    private String sortName;
    private String sortType;

    public BaseQueryParams() {
    }

    @JsonIgnore
    public String getOrderBy() {
        String sortBy = null;
        if (StrUtil.isNotBlank(this.sortName) && StrUtil.isNotBlank(this.sortType)) {
            sortBy = StrUtil.toUnderlineCase(this.sortName) + " " + this.sortType;
        }

        return sortBy;
    }

    public void setPageNum(final Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortName(final String sortName) {
        this.sortName = sortName;
    }

    public void setSortType(final String sortType) {
        this.sortType = sortType;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public String getSortName() {
        return this.sortName;
    }

    public String getSortType() {
        return this.sortType;
    }
}
