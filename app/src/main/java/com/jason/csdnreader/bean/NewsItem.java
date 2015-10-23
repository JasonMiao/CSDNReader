package com.jason.csdnreader.bean;

import java.io.Serializable;

/**
 * Created by zzmiao on 2015/10/23.
 */
public class NewsItem implements Serializable{
    /**
     * 资讯图片地址
     */
    private String pic;
    /**
     * 资讯标题
     */
    private String title;
    /**
     * 资讯内容简介
     */
    private String intro;

    public NewsItem(String pic, String title, String intro, String date) {
        this.pic = pic;
        this.title = title;
        this.intro = intro;
        this.date = date;
    }

    /**
     * 资讯发布时间
     */
    private String date;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
