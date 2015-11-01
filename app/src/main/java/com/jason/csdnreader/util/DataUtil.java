package com.jason.csdnreader.util;

import com.jason.csdnreader.bean.BlogItem;
import com.jason.csdnreader.bean.NewsItem;
import com.jason.csdnreader.bean.Profile;

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

    /**
     * 获取资讯列表
     * @param url
     * @return
     */
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

    //TODO 解析资讯


    /**
     * 获取博客列表
     * @param url
     * @return
     */
    public static List<BlogItem> getBlogList(String url) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = loadDocByUrl(url);
        Elements blogList = doc.getElementsByClass("blog_list");

        for (Element blogItem : blogList){
            BlogItem blog = new BlogItem();
            String link = blogItem.select("a").attr("href");
            String title = blogItem.select("h1").text();
            String pic = blogItem.select("dt").get(0).select("img").get(0).attr("src");
            String intro = blogItem.select("dd").text();
            String date = blogItem.getElementsByClass("time").get(0).text();
            String readtimes = blogItem.getElementsByClass("view").get(0).text();
            String comments = blogItem.getElementsByClass("comment").get(0).text();
            blog.setLink(link);
            blog.setTitle(title);
            blog.setPic(pic);
            blog.setIntro(intro);
            blog.setDate(date);
            blog.setReadtimes(readtimes);
            blog.setComments(comments);
            list.add(blog);
        }
        return list;
    }

    //TODO 解析博客内容

    //TODO 解析专栏

    /**
     * 解析个人资料
     * @param str
     * @return
     */
    public static Profile getProfile(String str){
        Profile profile = new Profile();
        Document doc = Jsoup.parse(str);
        Element info = doc.getElementsByClass("main").get(0);
        String pic_suffix = info.select("dt").get(0).select("img").get(0).attr("src"); // 获取到的头像地址为后缀，需要加上统一前缀
        String pic = URLUtil.MYCSDN + pic_suffix;
        String following = info.getElementsByClass("focus_num").get(0).select("a").text();
        String fans = info.getElementsByClass("fans_num").get(0).select("a").text();
        String nick_name = info.getElementsByClass("person-nick-name").get(0).select("span").text();
        String intro =  info.getElementsByClass("person-sign").get(0).text();
        String username = info.getElementsByClass("edit_intro").get(0).select("span").get(1).text();
        profile.setPic(pic);
        profile.setNick_name(nick_name);
        profile.setIntro(intro);
        profile.setFollowing(following);
        profile.setFans(fans);
        profile.setUsername(username);
        return profile;
    }
}
