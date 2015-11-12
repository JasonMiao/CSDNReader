package com.jason.csdnreader.bean;

import java.io.Serializable;

/**
 * Created by zzmiao on 2015/10/27.
 */
public class BlogItem implements Serializable {
    /**
     * 文章地址
     */
    private String link;
    /**
     * 博客类型(原创/转发)
     * 0-原创 1-转发 2-翻译
     */
    private int type;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 博客图片
     */
    private String pic;
    /**
     * 博客内容简介
     */
    private String intro;
    /**
     * 博客发布时间
     */
    private String date;
    /**
     * 阅读次数
     */
    private String readtimes;
    /**
     * 评论数

     */
    private String comments;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getReadtimes() {
        return readtimes;
    }

    public void setReadtimes(String readtimes) {
        this.readtimes = readtimes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
