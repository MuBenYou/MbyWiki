package com.mby.req;

public class PageReq {
    private int size;

    private int page;//当前页

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "size=" + size +
                ", page=" + page +
                '}';
    }
}