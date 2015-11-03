package com.jason.csdnreader.util;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Http工具类 单例模式
 *
 * 貌似Android SDK API22开始砍掉了HttpClient！！！所以只能使用第三方的httpclient
 * @author zzmiao
 * 
 */
public class HttpUtil {
	private static  CookieStore cookieStore = new BasicCookieStore();
	private static CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	private static HttpClientContext context = new HttpClientContext();

	private HttpUtil() {

	}

	public static String sendGet(String url) {
		CloseableHttpResponse response = null;
		String content = null;
		try {
			HttpGet get = new HttpGet(url);
			// 获取需要登录的页面内容时，需要添加请求头部
			get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
			get.setHeader("Accept-Language", "zh-cn");
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
			get.setHeader("Accept-Charset", "gzip, deflate");
			get.setHeader("Keep-Alive", "300");
			get.setHeader("Connection", "Keep-Alive");
//			get.setHeader("Cache-Control", "no-cache");
			response = httpClient.execute(get, context);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			if (response != null) {
				try {
					response.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return content;
	}

	public static String sendPost(String url, List<NameValuePair> nvps) {
		CloseableHttpResponse response = null;
		String content = null;
		try {
			HttpPost post = new HttpPost(url);
			if (nvps != null) {
				post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}
			response = httpClient.execute(post, context);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}
}
