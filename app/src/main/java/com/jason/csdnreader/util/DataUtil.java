package com.jason.csdnreader.util;

import android.util.Log;

import com.jason.csdnreader.bean.BlogItem;
import com.jason.csdnreader.bean.BloggerIntro;
import com.jason.csdnreader.bean.Category;
import com.jason.csdnreader.bean.Column;
import com.jason.csdnreader.bean.FollowItem;
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
    public static final String TAG = DataUtil.class.getSimpleName();

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
     *
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
     *
     * @param url
     * @return
     */
    public static List<BlogItem> getBlogList(String url) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = loadDocByUrl(url);
        Elements blogList = doc.getElementsByClass("blog_list");

        for (Element blogItem : blogList) {
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
     *
     * @param str
     * @return
     */
    public static Profile getProfile(String str) {
        Profile profile = new Profile();
        Document doc = Jsoup.parse(str);
        Element info = doc.getElementsByClass("main").get(0);
        String pic_suffix = info.select("dt").get(0).select("img").get(0).attr("src"); // 获取到的头像地址为后缀，需要加上统一前缀
        String pic = URLUtil.MYCSDN + pic_suffix;
        String following = info.getElementsByClass("focus_num").get(0).select("a").text();
        String fans = info.getElementsByClass("fans_num").get(0).select("a").text();
        String nick_name = info.getElementsByClass("person-nick-name").get(0).select("span").text();
        String intro = info.getElementsByClass("person-sign").get(0).text();
        String username = info.getElementsByClass("edit_intro").get(0).select("span").get(1).text();
        profile.setPic(pic);
        profile.setNick_name(nick_name);
        profile.setIntro(intro);
        profile.setFollowing(following);
        profile.setFans(fans);
        profile.setUsername(username);
        return profile;
    }

    /**
     * 解析关注者列表
     *
     * @param str
     * @return
     */
    public static List<FollowItem> getFollowingList(String str) {
        List<FollowItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(str);
        Elements followList = doc.getElementsByClass("col-sm-6");

        for (Element followItem : followList) {
            FollowItem follow = new FollowItem();
            String pic = followItem.select("img").get(0).attr("src");
            String username = followItem.select("img").get(0).attr("alt");
            String nick_name = followItem.select("h4").get(0).select("a").text();
            String intro = followItem.select("dd").get(0).select("p").text();
            follow.setPic(pic);
            follow.setUsername(username);
            follow.setNick_name(nick_name);
            follow.setIntro(intro);
            list.add(follow);
//            Log.e(TAG, follow.toString());
        }
        return list;
    }

    //TODO 解析Blogger展示页

    /**
     * 解析博主基本信息
     *
     * @param str
     * @return
     */
    public static BloggerIntro getBloggerIntro(String str) {
        BloggerIntro bloggerIntro = new BloggerIntro();
        Document doc = Jsoup.parse(str);
        Element info = doc.select("#panel_Profile").first();
        String pic = info.select("#blog_userface").first().select("img").first().attr("src");
        String pv = info.select("#blog_rank").first().select("li").get(0).select("span").first().text();
        String integral = info.select("#blog_rank").first().select("li").get(1).select("span").first().text();
        String level = info.select("#blog_rank").first().select("li").get(2).select("img").first().attr("src");
        String rank = info.select("#blog_rank").first().select("li").get(3).select("span").first().text();
        bloggerIntro.setIntegral(integral);
        bloggerIntro.setLevel(level);
        bloggerIntro.setRank(rank);
        bloggerIntro.setPv(pv);
        bloggerIntro.setPic(pic);
        return bloggerIntro;
    }

    /**
     * 解析专栏信息
     * 专栏详情页要处理分页 每页最多20条
     */
    public static List<Column> getColumn(String str) {
        List<Column> columns = new ArrayList<>();
        Document doc = Jsoup.parse(str);
        Element temp = doc.select("#sp_column").first();
        // 抓取数据的时候做好判断 博主是否有专栏
        if (null != temp) {
            Elements tempList = temp.select("table");
            int id = 1;
            int pId;
            for (Element tempItem : tempList) {
                String link = tempItem.select("a").get(1).attr("href");
                String label = tempItem.select("a").get(1).text();
                String desc = getNumbers(tempItem.select("p").first().text());
                // 本循环添加根节点
                columns.add(new Column(id, 0, label, desc, link));
                // columns添加一个成员后id加1
                pId = id;
                id++;
                // 开始解析专栏详情页
                int num = Integer.parseInt(desc);
                if (num <= 20) {
                    Document content = loadDocByUrl(link);
                    Elements columnList = content.getElementsByClass("blog_list");
                    for (Element column : columnList) {
                        String _link = column.select("a").get(1).attr("href");
                        String _label = column.select("a").get(1).text();
                        // 本循环添加叶节点 由于是两级树 所以叶节点desc属性应该不显示 处理方式为：传入""
                        columns.add(new Column(id, pId, _label, "", _link));
                        id++;
                    }
                } else { // 分页处理
                    for (int i = 1; i <= (num / 20 + 1); i++) {
                        Document content = loadDocByUrl(link + "?&page=" + i);
                        Elements columnList = content.getElementsByClass("blog_list");
                        for (Element column : columnList) {
                            String _link = column.select("a").get(1).attr("href");
                            String _label = column.select("a").get(1).text();
                            // 本循环添加叶节点 由于是两级树 所以叶节点desc属性应该不显示 处理方式为：传入""
                            columns.add(new Column(id, pId, _label, "", _link));
                            id++;
                        }
                    }
                }
            }
        }
        return columns;
    }

    /**
     * 解析博文分类信息
     * 分类详情页需要处理分页情况 每页最多15条
     *
     * @param str
     * @return
     */
    public static List<Category> getCategory(String str) {
        List<Category> categories = new ArrayList<>();
        Document doc = Jsoup.parse(str);
        Element columnOrCategory = doc.select("#panel_Category").first();
        // 排除既没有专栏也没有分类的博主
        if (null != columnOrCategory) {
            Element column = doc.select("#sp_column").first();
            Element temp;
            // 没有专栏的人 id=panel_Category的元素就一个 所以获取分类前判断下博主是否有专栏
            if (null != column) {
                temp = doc.select("#panel_Category").get(1).select("ul").get(1);
            } else {
                temp = doc.select("#panel_Category").get(0).select("ul").get(1);
            }
            // 抓取数据的时候做好判断 博主是否有分类
            if (null != temp) {
                Elements tempList = temp.select("li");
                int id = 1;
                int pId;
                for (Element tempItem : tempList) {
                    String link = URLUtil.BLOG_ + tempItem.select("a").first().attr("href");
                    String label = tempItem.select("a").first().text();
                    String desc = getNumbers(tempItem.select("span").first().text());
                    // 本循环增加根节点
                    categories.add(new Category(id, 0, label, desc, link));
                    pId = id;
                    id++;
                    // 开始解析分类详情页
                    int num = Integer.parseInt(desc);
                    if (num <= 15) {
                        Document content = loadDocByUrl(link);
                        Elements categoryList = content.getElementsByClass("article_item");
                        for (Element category : categoryList) {
                            String _link = URLUtil.BLOG_ + category.getElementsByClass("article_title").first().select("a").attr("href");
                            String _label = category.getElementsByClass("article_title").first().select("a").text();
                            // 本循环添加叶节点 由于是两级树 所以叶节点desc属性应该不显示 处理方式为：传入""
                            categories.add(new Category(id, pId, _label, "", _link));
                            id++;
                        }
                    } else { // 分页处理  但是考虑到循环所有分类的详情会很耗时耗流量 故不在一开始加载子节点 让用户自己选择去加载
//                    for (int i = 1; i <= (num / 15 + 1); i++) {
//                        Document content = loadDocByUrl(link + "/" + i);
//                        Elements categoryList = content.getElementsByClass("article_item");
//                        for (Element category : categoryList) {
//                            String _link = URLUtil.BLOG_ + category.getElementsByClass("article_title").first().select("a").attr("href");
//                            String _label = category.getElementsByClass("article_title").first().select("a").text();
//                            // 本循环添加叶节点 由于是两级树 所以叶节点desc属性应该不显示 处理方式为：传入""
//                            categories.add(new Category(id, pId, _label, "", _link));
//                            id++;
//                        }
//                    }
                    }
                }
            }
        }
//        Log.e("getCategory", categories.toString());
        return categories;
    }

    /**
     * 解析博主博客列表/分类更多详情
     * @param html
     * @return
     */
    public static List<BlogItem> getBlog(String html) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements blogList = doc.getElementsByClass("article_item");
        for (Element blog : blogList) {
            BlogItem blogItem = new BlogItem();
            Element temp = blog.getElementsByClass("article_title").first();
            String type = temp.select("span").get(0).attr("class");
            if ("ico ico_type_Original".equals(type))
                blogItem.setType(0);
            else if ("ico ico_type_Repost".equals(type))
                blogItem.setType(1);
            else
                blogItem.setType(2);
            String link = URLUtil.BLOG_ + temp.select("a").attr("href");
            String title = temp.select("a").first().text();
            String intro = blog.getElementsByClass("article_description").first().text();
            String date = blog.getElementsByClass("article_manage").select("span").get(0).text();
            String readtimes = "阅读" + blog.getElementsByClass("article_manage").select("span").get(1).text();
            String comments = "评论" + blog.getElementsByClass("article_manage").select("span").get(2).text();
            blogItem.setTitle(title);
            blogItem.setLink(link);
            blogItem.setIntro(intro);
            blogItem.setDate(date);
            blogItem.setReadtimes(readtimes);
            blogItem.setComments(comments);
            list.add(blogItem);
        }
        return list;
    }
}
