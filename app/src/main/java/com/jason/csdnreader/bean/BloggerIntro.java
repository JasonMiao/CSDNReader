package com.jason.csdnreader.bean;

import java.io.Serializable;

/**
 * Created by zzmiao on 2015/11/9.
 */
public class BloggerIntro implements Serializable{
    /**
     * 头像
     */
    private String pic;
    /**
     * 排名
     */
    private String rank;
    /**
     * 等级
     */
    private String level;
    /**
     * 访问量
     */
    private String pv;
    /**
     * 积分
     */
    private String integral;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
