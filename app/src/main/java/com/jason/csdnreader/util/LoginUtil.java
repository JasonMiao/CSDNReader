package com.jason.csdnreader.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
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

    public interface LoginCallback {
        public void onLoginResult(int result);
    }

    public static void doLogin(final String account, final String password, final LoginCallback callback) {
        HttpUtil.get(URLUtil.LOGIN_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String html = new String(responseBody, "UTF-8");
                    if (html.indexOf("redirect_back") > -1) { //有缓存直接登录
                        //TODO 登录成功后解析出个人资料信息-username,email等
                        callback.onLoginResult(SUCCESS);
                    } else {
                        Document doc = Jsoup.parse(html);
                        Element form = doc.select(".user-pass").get(0);
                        // 登录所需要的另外三个隐藏域
                        String lt = form.select("input[name=lt]").get(0).val();
                        String execution = form.select("input[name=execution]").get(0).val();
                        String _eventId = form.select("input[name=_eventId]").get(0).val();
                        RequestParams params = new RequestParams();
                        params.add("account", account);
                        params.add("password", password);
                        params.add("lt", lt);
                        params.add("execution", execution);
                        params.add("_eventId", _eventId);
                        HttpUtil.post(URLUtil.LOGIN_URL, params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                try {
                                    String ret = new String(responseBody, "UTF-8");
                                    if (ret.indexOf("redirect_back") > -1) {
                                        //TODO 登录成功后解析出个人资料信息-username,email等
                                        callback.onLoginResult(SUCCESS);
                                    } else if (ret.indexOf("登录太频繁") > -1) {
                                        callback.onLoginResult(BUSY);
                                    } else {
                                        callback.onLoginResult(FAILURE);
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
