package com.jason.csdnreader.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Http请求工具类
 *
 * Created by iNanHu on 2015/10/31.
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例化对象

    /**
     * 静态初始化
     */
    static {
        client.setTimeout(20000);
        client.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
    }

    /**
     * Get请求方式
     * @param url
     * @param res
     */
    public static void get(String url, AsyncHttpResponseHandler res) {
        client.get(url, res);
    }

    /**
     * 带参数的Post请求方式
     * @param url
     * @param params
     * @param res
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler res) {
        client.post(url, params, res);
    }

    /**
     * 返回请求客户端
     * @return
     */
    public static AsyncHttpClient getClient() {
        return client;
    }
}
