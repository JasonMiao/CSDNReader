package com.jason.csdnreader.app;

import android.app.Application;

import com.jason.csdnreader.util.HttpUtil;
import com.loopj.android.http.PersistentCookieStore;

import org.litepal.LitePalApplication;

/**
 * 整个应用的上下文对象（单例）
 *
 * Created by zzmiao on 2015/10/22.
 */
public class MyApplication extends Application {
    /**
     * 定义MyApplication对象
     */
    private static MyApplication myApplication = null;
    /**
     * android应用程序真正入口。
     * 此方法在所有activity service receiver组件之前调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        // 初始化数据库操作库
        LitePalApplication.initialize(this);
        // 设置网络请求持久化保存cookie
        HttpUtil.getClient().setCookieStore(new PersistentCookieStore(myApplication));
    }

    /**
     * 此方法方便在那些没有context对象的类中使用
     * @return MyApplication实例
     */
    public static MyApplication getMyApplication(){
        return myApplication;
    }
}
