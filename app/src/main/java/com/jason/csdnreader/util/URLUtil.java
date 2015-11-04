package com.jason.csdnreader.util;

/**
 * 解析地址工具类
 * Created by zzmiao on 2015/10/20.
 */
public class URLUtil {

    /**
     * 登录请求地址
     */
    public static  String LOGIN_URL = "https://passport.csdn.net/account/login";
    /**
     * 个人中心地址
     */
    public static String MYCSDN = "http://my.csdn.net";
    /**
     * 我关注对象地址
     */
    // http://my.csdn.net/service/main/my_relation?pageno=1&pagesize=50&type=follow --返回JSON字符串
    // http://my.csdn.net/my/follow/1 --也可以解析页面
    public static String MYFOLLOW = "http://my.csdn.net/my/follow/";
    public static String getMyFollowUrl(String page) {
        return MYFOLLOW + page;
    }
    /**
     * 我的收藏地址
     */
    // http://my.csdn.net/my/favorite/get_favorite_list?pageno=1&pagesize=10&username=bagecelia
    /**
     * 博客地址
     */
    public static String BLOG = "http://blog.csdn.net/";

    /**
     * 资讯地址工具类
     */
    public static class NewsUrl {
        public static String NEWS = "http://news.csdn.net/news";
        public static String MOBILE = "http://mobile.csdn.net/mobile";
        public static String CLOUD = "http://cloud.csdn.net/cloud";
        public static String DEVELOP = "http://sd.csdn.net/sd";
        public static String PROGRAMMER = "http://programmer.csdn.net/programmer";

        /**
         * 根据资讯类型和页码返回地址
         * @param newsType
         * @param page
         * @return
         */
        public static String getNewsUrl(int newsType, String page) {
            String url = "";
            switch (newsType) {
                case Constant.NEWS_TYPE.NEWS:
                    url = NEWS;
                    break;
                case Constant.NEWS_TYPE.MOBILE:
                    url = MOBILE;
                    break;
                case Constant.NEWS_TYPE.CLOUD:
                    url = CLOUD;
                    break;
                case Constant.NEWS_TYPE.DEVELOP:
                    url = DEVELOP;
                    break;
                case Constant.NEWS_TYPE.PROGRAMMER:
                    url = PROGRAMMER;
                    break;
                default:
                    break;
            }
            return url + "/" + page;
        }
    }

    /**
     * 博客地址
     */
    public static class BlogUrl {
        public static String BLOG = "http://blog.csdn.net";
        public static String MOBLIE = BLOG + "/mobile";
        public static String WEB = BLOG + "/web";
        public static String ENTERPRISE = BLOG + "/enterprise";
        public static String CODE = BLOG + "/code";
        public static String WWW = BLOG + "/www";
        public static String DATABASE = BLOG + "database";
        public static String SYSTEM = BLOG + "/system";
        public static String CLOUD = BLOG + "/cloud";
        public static String SOFTWARE = BLOG + "/software";
        public static String OTHER = BLOG + "/other";

        /**
         * 根据博客类型，排序方式和页码返回地址
         * @param blogType
         * @param sort 0表示按时间(/index.html)排序;1表示按热度(/newest.html)排序
         * @param page
         * @return
         */
        public static String getBlogUrl(int blogType, int sort, String page) {
            String url = "";
            switch (blogType){
                case Constant.BLOG_TYPE.MOBILE:
                    url = MOBLIE;
                    break;
                case Constant.BLOG_TYPE.WEB:
                    url = WEB;
                    break;
                case Constant.BLOG_TYPE.ENTERPRISE:
                    url = ENTERPRISE;
                    break;
                case Constant.BLOG_TYPE.CODE:
                    url = CODE;
                    break;
                case Constant.BLOG_TYPE.WWW:
                    url = WWW;
                    break;
                case Constant.BLOG_TYPE.DATABASE:
                    url = DATABASE;
                    break;
                case Constant.BLOG_TYPE.SYSTEM:
                    url = SYSTEM;
                    break;
                case Constant.BLOG_TYPE.CLOUD:
                    url = CLOUD;
                    break;
                case Constant.BLOG_TYPE.SOFTWARE:
                    url = SOFTWARE;
                    break;
                case Constant.BLOG_TYPE.OTHER:
                    url = OTHER;
                    break;
                default:
                    break;
            }
            if (sort == 0)
                url += "/index.html";
            else
                url += "/newest.html";
            return url + "?&page=" + page;
        }
    }
}


