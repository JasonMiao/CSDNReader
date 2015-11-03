package com.jason.csdnreader.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * 登录工具类
 *
 * Created by zzmiao on 2015/10/30.
 */
public class LoginUtil {
    public static final String TAG = "LoginUtil";
    // 登录成功
    public static final int SUCCESS = 0;
    // 登录失败
    public static final int FAILURE = 1;
    // 登录太频繁
    public static final int BUSY = 2;

    public static int doLogin(String account, String password) {
        /**************************
         ******第一步获取登录信息****
         **************************/
        String lt = "";
        String execution = "";
        String _eventId = "";
        String html = HttpUtil.sendGet(URLUtil.LOGIN_URL);
        Document doc = Jsoup.parse(html);
        Element form = doc.select(".user-pass").get(0);
        lt = form.select("input[name=lt]").get(0).val();
        execution = form.select("input[name=execution]").get(0).val();
        _eventId = form.select("input[name=_eventId]").get(0).val();
        /**************************
         ******第二步Post登录*******
         **************************/
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", account));
        nvps.add(new BasicNameValuePair("password", password));
        nvps.add(new BasicNameValuePair("lt", lt));
        nvps.add(new BasicNameValuePair("execution", execution));
        nvps.add(new BasicNameValuePair("_eventId", _eventId));
        String ret = HttpUtil.sendPost(URLUtil.LOGIN_URL, nvps);
        if (ret.indexOf("redirect_back") > -1) {
            return SUCCESS;
        } else if (ret.indexOf("登录太频繁") > -1) {
            return BUSY;
        } else {
            return FAILURE;
        }
    }
}
