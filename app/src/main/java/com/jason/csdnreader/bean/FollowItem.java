package com.jason.csdnreader.bean;

import java.io.Serializable;

/**
 * 关注的对象实体类
 *
 * Created by zzmiao on 2015/10/27.
 */
public class FollowItem implements Serializable{
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getIntro() {
        if ("".equals(intro))
            return "忘记写个人简介啦~~";
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return pic + "|" + username + "|" + nick_name + "|" + intro;
    }
}
