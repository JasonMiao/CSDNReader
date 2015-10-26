package com.jason.csdnreader.util;

import com.jason.csdnreader.bean.NewsItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络数据解析工具类
 * Created by zzmiao on 2015/10/26.
 */
public class DataUtil {
    /**
     * 根据URL获取Document
     *
     * @param url
     * @return
     */
    public static Document loadDocByUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(50000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 获取字符串中的数字
     *
     * @param content
     * @return
     */
    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static List<NewsItem> getNewsList(String url) {
        List<NewsItem> list = new ArrayList<>();
        Document doc = loadDocByUrl(url);
        Elements newsList = doc.getElementsByClass("unit");

        for (Element newsItem : newsList) {
            NewsItem news = new NewsItem();
            String title = newsItem.select("h1").text();
            String intro = newsItem.select("dd").text();
            String link = newsItem.select("a").attr("href");
            String date = newsItem.getElementsByClass("ago").get(0).text();
            String readtimes = getNumbers(newsItem.getElementsByClass("view_time").get(0).text());
            String comments = getNumbers(newsItem.getElementsByClass("num_recom").get(0).text());
            String pic;
            Elements imgs = newsItem.select("img");
            try {
                pic = imgs.get(0).attr("src");
            } catch (IndexOutOfBoundsException e) {
                pic = null;
            }
            news.setTitle(title);
            news.setIntro(intro);
            news.setLink(link);
            news.setDate(date);
            news.setReadtimes(readtimes);
            news.setComments(comments);
            news.setPic(pic);
            list.add(news);
        }
        return list;
    }
}
