package com.property_management.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    /*
    *  total: 100,             // 总记录数
       pages: 10,              // 总页数
       current: 1,             // 当前页
       size: 10,               // 每页大小
       records: []             // 数据列表
       * */

    private long total;
    private int pages;
    private int current;
    private int size;
    private List<T> records;

    public Page(long total, int pages, int current, int size, List<T> records) {
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.size = size;
        this.records = records;
    }
}
