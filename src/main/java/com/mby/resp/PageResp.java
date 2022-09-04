package com.mby.resp;

import java.util.List;

public class PageResp<T> {

    private List<T> list;

    private long total;//几条数据

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}