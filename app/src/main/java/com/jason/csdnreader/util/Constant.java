package com.jason.csdnreader.util;

/**
 * 常量定义
 * Created by zzmiao on 2015/9/24.
 */
public class Constant {
    /**
     * 资讯类型
     */
    public class NEWS_TYPE {
        /**
         * 业界
         */
        public static final int NEWS = 1;
        /**
         * 移动
         */
        public static final int MOBILE = 2;
        /**
         * 云计算
         */
        public static final int CLOUD = 3;
        /**
         * 软件研发
         */
        public static final int DEVELOP = 4;
        /**
         * 程序员
         */
        public static final int PROGRAMMER = 5;
    }

    /**
     * 博客类型
     */
    public class BLOG_TYPE {
        /**
         * 移动开发
         */
        public static final int MOBILE = 1;
        /**
         * Web前端
         */
        public static final int WEB = 2;
        /**
         * 架构设计
         */
        public static final int ENTERPRISE = 3;
        /**
         * 编程语言
         */
        public static final int CODE = 4;
        /**
         * 互联网
         */
        public static final int WWW = 5;
        /**
         * 数据库
         */
        public static final int DATABASE = 6;
        /**
         * 系统运维
         */
        public static final int SYSTEM = 7;
        /**
         * 云计算
         */
        public static final int CLOUD = 8;
        /**
         * 研发管理
         */
        public static final int SOFTWARE = 9;
        /**
         * 综合
         */
        public static final int OTHER = 10;
    }

    /**
     * 返回结果码定义
     */
    public class ResultCode{
        public static final int ERROR = 1;
        public static final int NO_DATA = 2;
        public static final int REFRESH = 3;
        public static final int LOAD = 4;
        public static final int FIRST = 5;
    }
}
