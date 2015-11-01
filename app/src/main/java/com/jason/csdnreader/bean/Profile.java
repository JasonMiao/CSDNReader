package com.jason.csdnreader.bean;

import java.io.Serializable;

/**
 * Created by zzmiao on 2015/10/29.
 */
public class Profile implements Serializable {
    /**
     * 头像
     */
    private String pic;
    /**
     * 昵称
     */
    private String nick_name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 个人简介
     */
    private String intro;
    /**
     * 关注数
     */
    private String following;
    /**
     * 粉丝数
     */
    private String fans;

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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }
}
