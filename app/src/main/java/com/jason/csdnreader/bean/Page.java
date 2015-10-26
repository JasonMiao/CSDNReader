package com.jason.csdnreader.bean;

/**
 * Created by zzmiao on 2015/10/26.
 */
public class Page {
    public int page = 1;
    public int contentMutiPages;
    public boolean contentFirstPage = true;

    public void setPageStart() {
        page = 2;
    }

    public void setPage(int num) {
        page = num;
    }

    public String getCurrentPage() {
        return String.valueOf(page);
    }

    public void addPage() {
        page++;
    }
}