package com.scy.util;

//分页的工具类
public class PageBeanUtil {
    private int page;//第几页--前端传过来的
    private int pageSize;//每一页的条数 也叫limit
    private int start;//索引  --计算出来的，所以不给他添加到构造方法里

    public PageBeanUtil(int page, int limit) {
        this.page=page;
        this.pageSize=limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    //这里的索引需要计算一下
    //索引=(页数-1)*条数
    public int getstart() {
        return (page-1)*pageSize;
    }

    public void setstart(int start) {
        start = start;
    }

}
